package com.delight.gaia.core.messaging.server.route;

import com.delight.gaia.base.message.GaiaMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.EqualsMethod;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.StackSize;
import net.bytebuddy.implementation.bytecode.collection.ArrayAccess;
import net.bytebuddy.implementation.bytecode.collection.ArrayFactory;
import net.bytebuddy.implementation.bytecode.constant.ClassConstant;
import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;
import net.bytebuddy.implementation.bytecode.constant.NullConstant;
import net.bytebuddy.implementation.bytecode.constant.TextConstant;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.implementation.bytecode.member.MethodInvocation;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.jar.asm.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GetParamRouteAppender implements ByteCodeAppender {
    final Method method;
    final Object object;


    @Override
    @SneakyThrows
    public Size apply(MethodVisitor methodVisitor, Implementation.Context implementationContext, MethodDescription instrumentedMethod) {
        Parameter[] parameters = method.getParameters();
        List<StackManipulation> stackManipulations = new ArrayList<>(parameters.length * 5);
        stackManipulations.add(IntegerConstant.forValue(parameters.length));
        stackManipulations.add(new NewReferenceArray(Object.class));
        stackManipulations.add(MethodVariableAccess.REFERENCE.storeAt(2));
        int i = 0;
        for (Parameter parameter : parameters) {
            stackManipulations.add(MethodVariableAccess.REFERENCE.loadFrom(2));
            stackManipulations.add(IntegerConstant.forValue(i));
            stackManipulations.add(MethodVariableAccess.REFERENCE.loadFrom(1));
            if (parameter.getType() == GaiaMessage.class) {
                stackManipulations.add(ArrayAccess.REFERENCE.store());
            } else {
                MessagingBody messagingBody = parameter.getAnnotation(MessagingBody.class);
                if (messagingBody != null) {
                    stackManipulations.add(new LoadClassType(parameter.getType()));
                    stackManipulations.add(MethodInvocation.invoke(new MethodDescription.ForLoadedMethod(GaiaMessage.class.getMethod("getBody", Class.class))));
                    stackManipulations.add(ArrayAccess.REFERENCE.store());
                } else {
                    MessagingAttributes messagingAttributes = parameter.getAnnotation(MessagingAttributes.class);
                    if (messagingAttributes != null) {
                        stackManipulations.add(MethodInvocation.invoke(new MethodDescription.ForLoadedMethod(GaiaMessage.class.getMethod("getAttributes"))));
                        stackManipulations.add(ArrayAccess.REFERENCE.store());
                    } else {
                        MessagingAttribute messagingAttribute = parameter.getAnnotation(MessagingAttribute.class);
                        if (messagingAttribute != null) {
                            stackManipulations.addAll(convert(parameter.getType(), messagingAttribute.value()));

                        } else
                            throw new IllegalArgumentException("[" + object.getClass().getName() + "." + method.getName() + "]@MessagingCommand method support only method with parameter is GaiaMessage.class or arg annotated by @MessagingBody or @MessagingAttributes  .");
                    }
                }
            }
            i++;
        }
        stackManipulations.add(MethodVariableAccess.REFERENCE.loadFrom(2));
        stackManipulations.add(MethodReturn.REFERENCE);
        StackManipulation.Size operandStackSize = new StackManipulation.Compound(
                stackManipulations
        ).apply(methodVisitor, implementationContext);
        return new Size(operandStackSize.getMaximalSize(),
                instrumentedMethod.getStackSize()  + 1);
    }

    List<StackManipulation> convert(Class type, String key) throws Exception {
        List<StackManipulation> stackManipulations = new ArrayList<>(4);
        StackManipulation convertStack;
        stackManipulations.add(new TextConstant(key));

        if (byte.class.equals(type) || Byte.class.equals(type)) {
            convertStack = MethodInvocation.invoke(new MethodDescription.ForLoadedMethod(GaiaMessage.class.getMethod("getByteAttribute", String.class)));
        } else if (Integer.class.equals(type) || int.class.equals(type)) {
            convertStack = MethodInvocation.invoke(new MethodDescription.ForLoadedMethod(GaiaMessage.class.getMethod("getIntAttribute", String.class)));
        } else if (Long.class.equals(type) || long.class.equals(type)) {
            convertStack = MethodInvocation.invoke(new MethodDescription.ForLoadedMethod(GaiaMessage.class.getMethod("getLongAttribute", String.class)));
        } else if (Double.class.equals(type) || double.class.equals(type)) {
            convertStack = MethodInvocation.invoke(new MethodDescription.ForLoadedMethod(GaiaMessage.class.getMethod("getDoubleAttribute", String.class)));
        } else if (Boolean.class.equals(type) || boolean.class.equals(type)) {
            convertStack = MethodInvocation.invoke(new MethodDescription.ForLoadedMethod(GaiaMessage.class.getMethod("getBoolAttribute", String.class)));
        } else if (Short.class.equals(type) || short.class.equals(type)) {
            convertStack = MethodInvocation.invoke(new MethodDescription.ForLoadedMethod(GaiaMessage.class.getMethod("getShortAttribute", String.class)));
        } else {
            convertStack = MethodInvocation.invoke(new MethodDescription.ForLoadedMethod(GaiaMessage.class.getMethod("getAttribute", String.class)));
        }
        stackManipulations.add(convertStack);
        stackManipulations.add(ArrayAccess.REFERENCE.store());
        return stackManipulations;
    }

    @RequiredArgsConstructor
    class LoadClassType implements StackManipulation {
        final Class type;


        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public Size apply(MethodVisitor methodVisitor, Implementation.Context implementationContext) {
            methodVisitor.visitLdcInsn(Type.getType(type));
            return StackSize.SINGLE.toIncreasingSize();
        }
    }

    class NewReferenceArray implements StackManipulation {
        final Class type;

        public NewReferenceArray(Class type) {
            this.type = type;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public Size apply(MethodVisitor methodVisitor, Implementation.Context implementationContext) {
            methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, Type.getType(type).getInternalName());
            return StackSize.ZERO.toDecreasingSize();
        }
    }

    class IfNull implements StackManipulation {
        private final Label label;

        public IfNull(Label label) {
            this.label = label;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public StackManipulation.Size apply(MethodVisitor mv, Implementation.Context ctx) {
            mv.visitJumpInsn(Opcodes.IFNULL, label);
            return new StackManipulation.Size(-1, 0);
        }
    }

    class GoTo implements StackManipulation {
        private final Label label;

        public GoTo(Label label) {
            this.label = label;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public StackManipulation.Size apply(MethodVisitor mv, Implementation.Context ctx) {
            mv.visitJumpInsn(Opcodes.GOTO, label);
            return new StackManipulation.Size(0, 0);
        }
    }

    class Mark implements StackManipulation {
        private final Label label;

        public Mark(Label label) {
            this.label = label;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public StackManipulation.Size apply(MethodVisitor mv, Implementation.Context ctx) {
            mv.visitLabel(label);
            return new StackManipulation.Size(0, 0);
        }
    }
}
