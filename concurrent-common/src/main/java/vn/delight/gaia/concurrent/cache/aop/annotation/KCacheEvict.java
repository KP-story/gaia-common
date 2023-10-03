package vn.delight.gaia.concurrent.cache.aop.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KCacheEvict {

    /**
     * map name
     */
    String cacheName() default "";

    /**
     * key using spring epl
     */
    String key() default "";

    /**
     * map name
     */
    String cacheNameEpl() default "";

    /**
     * key using spring epl
     */
    String keyEpl() default "";

    Class keyType() default String.class;

    boolean allEntries() default false;

    String factory() default "";

}
