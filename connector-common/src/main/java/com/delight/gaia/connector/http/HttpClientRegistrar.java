package com.delight.gaia.connector.http;

import com.delight.gaia.connector.http.config.EnableHttpClients;
import com.delight.gaia.connector.http.config.HttpConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HttpClientRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
    private Map<String, HttpClientFactory> httpFactories = new HashMap<>();
    private ResourceLoader resourceLoader;
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<HttpConfig> loadConfig() {
        return Binder.get(environment)
                .bind("http-clients", Bindable.listOf(HttpConfig.class))
                .orElse(null);
    }

    public void registerHttpFactories(BeanDefinitionRegistry registry) {
        loadConfig().forEach(httpConfig -> {
            try {
                registerHttpFactory(httpConfig, registry);
            } catch (Exception e) {
                log.error("error when init httpClientFactory {} error", httpConfig, e);
            }
        });


    }

    public HttpClientFactory getFactory(String name) {
        return httpFactories.get(name);
    }

    public void registerHttpFactory(HttpConfig httpConfig, BeanDefinitionRegistry registry) throws Exception {
        Class factoryClass = Class.forName(httpConfig.getFactory());
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(factoryClass).setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE)
                .setLazyInit(true);
        builder.addConstructorArgValue(httpConfig);
        registry.registerBeanDefinition(getFactoryName(httpConfig.getName()),
                builder.getBeanDefinition());
    }

    private String getFactoryName(String name) {
        return name + "GaiaHttpClientFactory";

    }


    public void registerClients(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        LinkedHashSet<BeanDefinition> candidateComponents = new LinkedHashSet<>();
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(HttpClient.class));
        Set<String> basePackages = getBasePackages(metadata);
        for (String basePackage : basePackages) {
            candidateComponents.addAll(scanner.findCandidateComponents(basePackage));
        }
        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                // verify annotated class is an interface
                AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                Assert.isTrue(annotationMetadata.isInterface(), "@HttpClient can only be specified on an interface");
                Map<String, Object> attributes = annotationMetadata
                        .getAnnotationAttributes(HttpClient.class.getCanonicalName());
                registerClient(registry, annotationMetadata, attributes);
            }
        }
    }


    public void registerClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata,
                               Map<String, Object> attributes) {

        String className = annotationMetadata.getClassName();
        Class clazz = ClassUtils.resolveClassName(className, null);
        ConfigurableBeanFactory beanFactory = registry instanceof ConfigurableBeanFactory
                ? (ConfigurableBeanFactory) registry : null;
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(clazz, () -> {
            HttpClientFactory httpClientFactory = (HttpClientFactory) beanFactory.getBean(getFactoryName(attributes.get("value").toString()));
            return httpClientFactory.createService(clazz);
        });
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        definition.setLazyInit(true);
        registry.registerBeanDefinition(className, definition.getBeanDefinition());
    }


    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
    }

    protected Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(EnableHttpClients.class.getCanonicalName());

        Set<String> basePackages = new HashSet<>();
        for (String pkg : (String[]) attributes.get("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (Class<?> clazz : (Class[]) attributes.get("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }
        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }

    private String getQualifier(Map<String, Object> client) {
        if (client == null) {
            return null;
        }
        String qualifier = (String) client.get("qualifier");
        if (StringUtils.hasText(qualifier)) {
            return qualifier;
        }
        return null;
    }

    private String[] getQualifiers(Map<String, Object> client) {
        if (client == null) {
            return null;
        }
        List<String> qualifierList = new ArrayList<>(Arrays.asList((String[]) client.get("qualifiers")));
        qualifierList.removeIf(qualifier -> !StringUtils.hasText(qualifier));
        if (qualifierList.isEmpty() && getQualifier(client) != null) {
            qualifierList = Collections.singletonList(getQualifier(client));
        }
        return !qualifierList.isEmpty() ? qualifierList.toArray(new String[0]) : null;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        registerHttpFactories(registry);
        registerClients(metadata, registry);
    }
}
