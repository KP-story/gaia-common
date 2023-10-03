package vn.delight.gaia.concurrent.cache.aop.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KCachePut {

    /**
     * map name
     */
    String cacheName() default "";

    /**
     * key using spring epl
     */
    String key() default "";

    long ttl() default -1;

    long idle() default -1;

    /**
     * map name
     */
    String cacheNameEpl() default "";

    /**
     * key using spring epl
     */
    String keyEpl() default "";

    long maxSize() default -1;

    Class keyType() default String.class;

    String factory() default "";


}
