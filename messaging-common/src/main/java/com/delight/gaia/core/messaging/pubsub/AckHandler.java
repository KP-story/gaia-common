package com.delight.gaia.core.messaging.pubsub;

import reactor.core.publisher.Mono;

public interface AckHandler {

    Mono<Void> ack();

    Mono<Void> unAck();
}
