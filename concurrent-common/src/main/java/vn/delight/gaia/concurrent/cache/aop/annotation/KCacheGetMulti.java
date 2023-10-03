package vn.delight.gaia.concurrent.cache.aop.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KCacheGetMulti {

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
    String[] key() default {};

    Class keyType() default String.class;

    String factory() default "";


}