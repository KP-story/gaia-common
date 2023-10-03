package com.delight.gaia.core.messaging.client.configuration;

import com.delight.gaia.core.messaging.client.ClientSocketConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MessagingClientRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public List<ClientSocketConfig> loadConfig() {
        return Binder.get(environment)
                .bind("messaging-clients", Bindable.listOf(ClientSocketConfig.class))
                .orElse(null);
    }

    public void registerClients(BeanDefinitionRegistry registry) {
        loadConfig().forEach(config -> {
            try {
                registerClients(config, registry);
            } catch (Exception e) {
                log.error("error when registerClients {} error", config, e);
            }
        });


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

    public void registerClients(ClientSocketConfig clientConfig, BeanDefinitionRegistry registry) throws Exception {
        Class factoryClass = Class.forName(clientConfig.getType());
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(factoryClass).setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE)
                .setLazyInit(true);
        registerCodec(clientConfig.getCodecClass(), registry);
        ConfigurableBeanFactory beanFactory = registry instanceof ConfigurableBeanFactory
                ? (ConfigurableBeanFactory) registry : null;
        builder.addConstructorArgValue(beanFactory.getBean(clientConfig.getCodecClass()));
        builder.addConstructorArgValue(clientConfig);
        registry.registerBeanDefinition(getName(clientConfig.getName()),
                builder.getBeanDefinition());
    }

    private String getName(String name) {
        return name;
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        registerClients(registry);
    }
}
