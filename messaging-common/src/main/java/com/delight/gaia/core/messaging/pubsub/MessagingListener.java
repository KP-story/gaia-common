package com.delight.gaia.core.messaging.pubsub;

import com.delight.gaia.base.message.GaiaMessage;
import reactor.core.publisher.Mono;

public interface MessagingListener {
    String topic();

    String group();

    Mono<Void> onMessage(GaiaMessage message, AckHandler ackHandler);

    default String broker() {
        return null;
    }
}
