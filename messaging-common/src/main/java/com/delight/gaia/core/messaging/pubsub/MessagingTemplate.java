package com.delight.gaia.core.messaging.pubsub;

import com.delight.gaia.base.message.GaiaMessage;
import reactor.core.publisher.Mono;

public interface MessagingTemplate {
    Mono<Boolean> publish(String topic, String group, GaiaMessage gaiaMessage);
}
