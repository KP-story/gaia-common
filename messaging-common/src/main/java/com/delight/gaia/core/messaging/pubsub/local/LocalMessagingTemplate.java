package com.delight.gaia.core.messaging.pubsub.local;

import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.core.messaging.pubsub.MessagingTemplate;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RequiredArgsConstructor
public class LocalMessagingTemplate implements MessagingTemplate {
    private final Sinks.Many<LocalEvent> sink;

    @Override
    public Mono<Boolean> publish(String topic, String group, GaiaMessage gaiaMessage) {
        Sinks.EmitResult emitResult = sink.tryEmitNext(new LocalEvent().setTopic(topic).setEvent(gaiaMessage));
        if (emitResult.isSuccess()) {
            return Mono.just(true);
        } else {
            return Mono.just(false);
        }
    }
}
