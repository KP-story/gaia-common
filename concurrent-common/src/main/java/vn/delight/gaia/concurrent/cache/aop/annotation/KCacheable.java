package vn.delight.gaia.concurrent.cache.aop.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KCacheable {

    /**
     * map name
     */
    String cacheName() default "";

    /**
     * map name
     */
    String cacheNameEpl() default "";

    /**
     * key using spring epl
     */
    String keyEpl() default "";

    /**
     * key using spring epl
     */
    String key() default "";

    Class keyType() default String.class;

    long ttl() default -1;

    long idle() default -1;

    long maxSize() default -1;

    String factory() default "";


}