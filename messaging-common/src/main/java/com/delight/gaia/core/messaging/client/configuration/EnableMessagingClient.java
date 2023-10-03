package com.delight.gaia.core.messaging.client.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MessagingClientRegistrar.class)
public @interface EnableMessagingClient {
}
