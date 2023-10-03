package com.delight.gaia.core.messaging.pubsub;

import com.delight.gaia.core.messaging.pubsub.local.FluxBroker;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

import java.util.HashSet;
import java.util.Set;
@Component
public class BrokerRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware, ApplicationContextAware, BeanPostProcessor {
    private ApplicationContext applicationContext;

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private Set<BrokerConfig> loadConfiguration() {
        return Binder.get(environment)
                .bind("brokers", Bindable.setOf(BrokerConfig.class))
                .orElse(new HashSet<>());
    }

    void registerBroker(BeanDefinitionRegistry registry) {
        Set<BrokerConfig> brokerConfigs = loadConfiguration();
        ConfigurableBeanFactory beanFactory = registry instanceof ConfigurableBeanFactory
                ? (ConfigurableBeanFactory) registry : null;
        brokerConfigs.forEach(brokerConfig -> {
            switch (brokerConfig.getEngine()) {
                case LOCAL_FLUX:
                    BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(Broker.class, () -> {
                        Broker broker = new FluxBroker();
                        broker.start().publishOn(Schedulers.boundedElastic()).subscribe();
                        return broker;
                    });
                    definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
                    definition.setLazyInit(true);
                    registry.registerBeanDefinition(getBrokerName(brokerConfig.getName()), definition.getBeanDefinition());
                    registerTemplate(brokerConfig, registry, beanFactory);
                    return;
            }

        });

    }

    void registerTemplate(BrokerConfig brokerConfig, BeanDefinitionRegistry registry, BeanFactory beanFactory) {
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(MessagingTemplate.class, () -> {
            Broker broker = beanFactory.getBean(getBrokerName(brokerConfig.getName()), Broker.class);
            return broker.getTemplate();
        });
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        definition.setLazyInit(true);
        registry.registerBeanDefinition(getTemplateName(brokerConfig.getName()), definition.getBeanDefinition());

    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof MessagingListener) {
            Broker broker = applicationContext.getBean(getBrokerName(((MessagingListener) bean).broker()), Broker.class);
            if (broker != null) {
                broker.listener((MessagingListener) bean);
            }
        }
        return bean;
    }


    String getBrokerName(String name) {
        return name + "GaiaBroker";
    }

    String getTemplateName(String name) {
        return name + "GaiaTemplate";
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        registerBroker(registry);

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
