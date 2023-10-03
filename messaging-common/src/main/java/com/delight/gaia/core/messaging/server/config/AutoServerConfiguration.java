package com.delight.gaia.core.messaging.server.config;

import com.delight.gaia.base.message.GaiaMessageCodec;
import com.delight.gaia.core.messaging.MessageHandler;
import com.delight.gaia.core.messaging.client.ClientSocketConfig;
import com.delight.gaia.core.messaging.server.rsocket.RsServer;
import io.rsocket.Payload;
import lombok.SneakyThrows;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

@Configuration(proxyBeanMethods = false)
public class AutoServerConfiguration implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public ServerSocketConfig loadConfig() {
        return Binder.get(environment)
                .bind("messaging-server", Bindable.of(ServerSocketConfig.class))
                .orElse(new ServerSocketConfig());
    }


    private void registerCodec(String encoderClass, BeanDefinitionRegistry registry) throws Exception {
        Class factoryClass = Class.forName(encoderClass);
        if (!registry.isBeanNameInUse(factoryClass.getName())) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(factoryClass).setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE)
                    .setLazyInit(true);
            registry.registerBeanDefinition(factoryClass.getName(),
                    builder.getBeanDefinition());
        }
    }

    public void registerServer(BeanDefinitionRegistry registry) throws Exception {
        ServerSocketConfig clientConfig = loadConfig();
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RsServer.class).setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        registerCodec(clientConfig.getCodecClass(), registry);
        ConfigurableBeanFactory beanFactory = registry instanceof ConfigurableBeanFactory
                ? (ConfigurableBeanFactory) registry : null;
        builder.addConstructorArgValue(beanFactory.getBeanProvider(MessageHandler.class).getObject());
        builder.addConstructorArgValue(clientConfig);
        builder.addConstructorArgValue(beanFactory.getBean(clientConfig.getCodecClass()));
        registry.registerBeanDefinition(RsServer.class.getName(),
                builder.getBeanDefinition());
    }


    @SneakyThrows
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        registerServer(registry);
    }
}


