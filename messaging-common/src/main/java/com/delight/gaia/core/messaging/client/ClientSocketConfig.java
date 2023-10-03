package com.delight.gaia.core.messaging.client;

import com.delight.gaia.base.vo.RemoteAddr;
import com.delight.gaia.base.vo.RetryBackoff;
import com.delight.gaia.core.messaging.client.rsocket.RSocketMessagingClient;
import com.delight.gaia.core.messaging.codec.ProtobufCodec;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientSocketConfig {
    private int timeout = 500;
    private int connectTimeout = 30000;
    private int maxConnection = 10;
    private int idleTime = 60;
    private RetryBackoff retryBackoff;
    private List<RemoteAddr> servers;
    private String name;
    private String lbStrategy = "ROUND_ROBIN";
    private String type= RSocketMessagingClient.class.getName();
    private String codecClass= ProtobufCodec.class.getName();
}

