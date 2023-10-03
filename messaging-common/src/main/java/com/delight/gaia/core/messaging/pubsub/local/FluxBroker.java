package com.delight.gaia.core.messaging.pubsub.local;

import com.delight.gaia.core.messaging.pubsub.Broker;
import com.delight.gaia.core.messaging.pubsub.MessagingListener;
import com.delight.gaia.core.messaging.pubsub.MessagingTemplate;
import org.springframework.beans.factory.DisposableBean;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluxBroker implements Broker, DisposableBean {
    private final Sinks.Many<LocalEvent> sink = Sinks.many().unicast().onBackpressureBuffer();
    protected MessagingTemplate messagingTemplate = new LocalMessagingTemplate(sink);
    protected Map<String, List<MessagingListener>> listeners = new HashMap<>();


    @Override
    public Mono<Void> start() {
        return sink.asFlux().doOnNext(localEvent -> {

            List<MessagingListener> messagingListeners = listeners.get(localEvent.getTopic());

            if (messagingListeners != null)
                messagingListeners.forEach(messagingListener -> messagingListener.onMessage(localEvent.getEvent(), null).subscribe());
        }).then();
    }

    @Override
    public MessagingTemplate getTemplate() {
        return messagingTemplate;
    }

    @Override
    public void listener(MessagingListener messagingListener) {
        List<MessagingListener> messagingListeners = listeners.computeIfAbsent(messagingListener.topic(), s -> new ArrayList<>());
        messagingListeners.add(messagingListener);
    }

    @Override
    public void destroy() throws Exception {
        sink.tryEmitComplete();
    }
}
