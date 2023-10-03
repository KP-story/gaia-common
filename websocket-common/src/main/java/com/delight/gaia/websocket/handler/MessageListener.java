package com.delight.gaia.websocket.handler;

import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.websocket.session.WebsocketSession;
import reactor.core.publisher.Mono;

public interface MessageListener {
    Mono<Void> onMessage(GaiaMessage websocketMessage, WebsocketSession websocketSession);
}
