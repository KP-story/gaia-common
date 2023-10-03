package com.delight.gaia.websocket.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "websocket")
public class SessionConfig {
    private String endpoint;
    private Integer maxConnection = 10000;
    private Integer connectionTTL = null;
    private Integer connectionIdleTime = 3*60*1000;
    private Class codec;
}
