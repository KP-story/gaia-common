package com.delight.gaia.core.messaging.server.route;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(AutoRouter.class)
public @interface EnableMessagingAutoRoute {
}
