package com.delight.gaia.jpa.config;

import com.delight.gaia.base.utility.GaiaClassUtils;
import com.delight.gaia.jpa.converter.JsonReadConverter;
import com.delight.gaia.jpa.converter.JsonWriteConverter;
import com.delight.gaia.jpa.converter.annotation.JsonType;
import com.delight.gaia.jpa.converter.annotation.TypeDef;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;


@Configuration
public class ConverterRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware {
    private Environment environment;
    private ResourceLoader resourceLoader;
    private Map<String, Class> readConverters = new HashMap<>();
    private Map<String, Class> writeConverters = new HashMap<>();

    @SneakyThrows
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerConverterFactories(importingClassMetadata, registry);
    }


    private void registerConverterFactories(AnnotationMetadata metadata, BeanDefinitionRegistry registry) throws ClassNotFoundException {
        LinkedHashSet<BeanDefinition> candidateComponents = new LinkedHashSet<>();
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(TypeDef.class));
        Set<String> basePackages = getBasePackages(metadata);

        for (String basePackage : basePackages) {
            candidateComponents.addAll(scanner.findCandidateComponents(basePackage));
        }
        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                Class entityClass = Class.forName(candidateComponent.getBeanClassName());
                List<Field> fields = GaiaClassUtils.listAllFields(entityClass, JsonType.class);
                fields.forEach(field -> {
                    registerFactory(registry, entityClass, field);
                });

            }
        }
    }

    @SneakyThrows
    private void registerFactory(BeanDefinitionRegistry registry, Class entityClass, Field field) {
        JsonType annotation = field.getAnnotation(JsonType.class);
        Class rawType = annotation.value();
        registerJsonReadFactory(registry, field.getName(), field.getGenericType(), rawType, entityClass);
        registerJsonWriteFactory(registry, field.getName(), field.getGenericType(), rawType, entityClass);
    }

    private void registerJsonWriteFactory(BeanDefinitionRegistry registry, String name, Type type, Type rawJsonType, Class entity
    ) throws IOException {
        if (writeConverters.containsKey(type.getTypeName())) {
            return;
        }
        Class c = buildFactory(name, type, rawJsonType, entity, JsonWriteConverter.class, WritingConverter.class);
        writeConverters.put(type.getTypeName(), c);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(c).setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE)
                .setLazyInit(true);
        registry.registerBeanDefinition(c.getName(),
                builder.getBeanDefinition());

    }

    private void registerJsonReadFactory(BeanDefinitionRegistry registry, String name, Type type, Type rawJsonType, Class entity
    ) throws IOException {

        if (readConverters.containsKey(type.getTypeName())) {
            return;
        }
        Class c = buildFactory(name, type, rawJsonType, entity, JsonReadConverter.class, ReadingConverter.class);
        readConverters.put(type.getTypeName(), c);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(c).setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE)
                .setLazyInit(true);
        registry.registerBeanDefinition(c.getName(),
                builder.getBeanDefinition());


    }

    private Class buildFactory(String name, Type type, Type rawJsonType, Class entity, Class subFactory, Class annotation) throws IOException {
        DynamicType.Unloaded<?> make = new ByteBuddy().makePackage(entity.getPackageName()).make();
        DynamicType.Builder factorBuilder = new ByteBuddy()
                .subclass(TypeDescription.Generic.Builder.parameterizedType(subFactory, type, rawJsonType).build())
                .name(entity.getSimpleName()+"_"+name + subFactory.getSimpleName()).annotateType(AnnotationDescription.Builder.ofType(annotation)
                        .build());
        factorBuilder.make().include(make).saveIn(new File("./.autoBuild/"));
        Class proxyClass = factorBuilder.make().load(getClass().getClassLoader())
                .getLoaded();
        return proxyClass;
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
                .getAnnotationAttributes(EnableCustomType.class.getCanonicalName());
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
    public void setEnvironment(Environment environment) {
        this.environment = environment;

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
