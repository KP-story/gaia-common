package com.delight.gaia.core.messaging.server.route;

import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.exception.CommandFailureException;
import com.delight.gaia.base.exception.IllegalReturnClassException;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.session.RemoteSession;
import com.delight.gaia.base.utility.GaiaClassUtils;
import com.delight.gaia.core.messaging.MessageHandler;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Configurable
@RequiredArgsConstructor
public class AutoRouter implements MessageHandler, BeanPostProcessor {
    Map<String, IMethodHandler> methods = new HashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
       if( bean.getClass().isAnnotationPresent(MessagingController.class))
       {
           Class<?> objClz = bean.getClass();
           if (org.springframework.aop.support.AopUtils.isAopProxy(bean)) {
               objClz = org.springframework.aop.support.AopUtils.getTargetClass(bean);
           }
           loadMethod(bean, objClz);
       }
        return bean;
    }



    private void loadMethod(Object obj, Class<?> objClz) {
        for (Method m : objClz.getDeclaredMethods()) {
            MessagingCommand messagingCommand = m.getAnnotation(MessagingCommand.class);
            if (messagingCommand != null) {
                if (methods.containsKey(messagingCommand.value())) {
                    throw new RouteDuplicateException("[" + messagingCommand.value() + "]@Command is duplicated.");
                }
                IMethodHandler iMethodHandler = buildRouteMethodHandler(obj, m);
                methods.put(messagingCommand.value(), iMethodHandler);
            }
        }
    }

    @SneakyThrows
    private IMethodHandler buildRouteMethodHandler(Object object, Method method) {
        if ((method.getReturnType() != Mono.class)) {
            throw new IllegalReturnClassException("[" + object.getClass().getName() + "." + method.getName() + "]@MessagingCommand method support only method with Mono return class.");
        }
        Implementation getParamMethodImplementation;
        int parametersSize = method.getParameterCount();
        if (parametersSize == 0) {
            getParamMethodImplementation = FixedValue.nullValue();
        } else {
            getParamMethodImplementation = new Implementation() {
                @Override
                public InstrumentedType prepare(InstrumentedType instrumentedType) {
                    return instrumentedType;
                }

                @Override
                public ByteCodeAppender appender(Target implementationTarget) {
                    return new GetParamRouteAppender(method, object);
                }
            };
        }

        DynamicType.Unloaded<?> make = new ByteBuddy().makePackage(object.getClass().getPackageName()).make();
        DynamicType.Builder factorBuilder = new ByteBuddy()
                .subclass(IMethodHandler.class)
                .name(method.getName() + "MethodHandler").defineMethod("getParameters", Object[].class, Modifier.PUBLIC).withParameters(GaiaMessage.class).intercept(getParamMethodImplementation);
        Type returnType = GaiaClassUtils.getFirstGeneric(method.getGenericReturnType());
        Implementation handlerImplementation;
        if (returnType == Void.class || returnType == GaiaMessage.class) {
            handlerImplementation = MethodCall.invoke(IMethodHandler.class.getMethod("_handle", GaiaMessage.class)).withArgument(0);
        } else {
            handlerImplementation = MethodCall.invoke(IMethodHandler.class.getMethod("_handleWrap", GaiaMessage.class)).withArgument(0);
        }
        factorBuilder = factorBuilder.defineMethod("handle", Mono.class, Modifier.PUBLIC).withParameters(GaiaMessage.class).throwing(Exception.class).intercept(handlerImplementation);
        factorBuilder.make().include(make).saveIn(new File("./.autoBuild/"));
        Class proxyClass = factorBuilder.make().include(make).load(object.getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER_PERSISTENT.with(PackageDefinitionStrategy.Trivial.INSTANCE))
                .getLoaded();
        IMethodHandler iMethodHandler = (IMethodHandler) proxyClass.getConstructor().newInstance();
        iMethodHandler.init(method, object);
        return iMethodHandler;
    }

    @Override
    public Mono<GaiaMessage> handleReply(GaiaMessage gaiaMessage, RemoteSession remoteSession) throws Exception {
        IMethodHandler iMethodHandler = methods.get(gaiaMessage.getCommand());
        if (iMethodHandler == null) {
            throw new CommandFailureException(MessageCode.NOT_FOUND, gaiaMessage.getCommand());
        }
        return iMethodHandler.handle(gaiaMessage);
    }

    @Override
    public Mono<Void> handle(GaiaMessage gaiaMessage, RemoteSession remoteSession) throws Exception {
        IMethodHandler iMethodHandler = methods.get(gaiaMessage.getCommand());
        if (iMethodHandler == null) {
            throw new CommandFailureException(MessageCode.NOT_FOUND, gaiaMessage.getCommand());
        }
        return iMethodHandler.handle(gaiaMessage);
    }

//    public static void main(String[] args) {
//        AutoRouter autoRouter = new AutoRouter();
//        Demo demo = new Demo();
//        autoRouter.loadMethod(demo, demo.getClass());
//    }

//    public static class Demo {
//        //        @MessagingCommand("demo1")
////        public Mono<Boolean> run() {
////            return Mono.just(true);
////        }
////
////        @MessagingCommand("demo2")
////        public Mono<Void> run2() {
////            return Mono.just(true).then();
////        }
////
////        @MessagingCommand("demo3")
////        public Mono<GaiaMessage> run3() {
////            return Mono.just(new GaiaMessage());
////        }
////
//        @MessagingCommand("demo4")
//        public Mono<GaiaMessage> run4(GaiaMessage gaiaMessage, @MessagingBody String body, @MessagingAttributes Map<String, String> attributed) {
//            return Mono.just(new GaiaMessage());
//        }
//
//        @MessagingCommand("demo5")
//        public Mono<Void> run5(GaiaMessage gaiaMessage, @MessagingBody String body, @MessagingAttributes Map<String, String> attributed, @MessagingAttribute("demo") String a, @MessagingAttribute("demo2") int r) {
//            return Mono.just(new GaiaMessage()).then();
//        }
//    }

}
