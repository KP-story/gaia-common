package com.delight.gaia.websocket.handler;

import com.delight.gaia.websocket.session.WebsocketSession;
import reactor.core.publisher.Mono;

public interface WebsocketEventListener {
    Mono<Void> onConnect(WebsocketSession websocketSession);

    Mono<Void> onDisconnect(WebsocketSession websocketSession);
}
