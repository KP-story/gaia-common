package com.delight.gaia.core.messaging.server.config;

import com.delight.gaia.base.vo.RemoteAddr;
import com.delight.gaia.base.vo.RetryBackoff;
import com.delight.gaia.core.messaging.client.rsocket.RSocketMessagingClient;
import com.delight.gaia.core.messaging.codec.ProtobufCodec;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
public class ServerSocketConfig {
    private int timeout = 500;
    private int initTimeout = 30000;
    private int maxConnection = 10;
    private int idleTime = 60;
    private RemoteAddr address = new RemoteAddr("0.0.0.0", 5432);
    private String codecClass = ProtobufCodec.class.getName();
}

