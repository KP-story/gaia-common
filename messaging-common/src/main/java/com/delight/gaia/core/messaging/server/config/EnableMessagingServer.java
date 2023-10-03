package com.delight.gaia.core.messaging.server.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(AutoServerConfiguration.class)
public @interface EnableMessagingServer {
}
