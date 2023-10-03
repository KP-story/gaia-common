package com.delight.gaia.core.messaging.pubsub;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrokerConfig {
    private  BrokerEngine engine;
    private String credential;
    private String name;
}
