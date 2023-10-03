package com.delight.gaia.core.messaging.pubsub;

import reactor.core.publisher.Mono;

public interface Broker {
    Mono<Void> start();

    MessagingTemplate getTemplate();

    void listener(MessagingListener messagingListener);
}
