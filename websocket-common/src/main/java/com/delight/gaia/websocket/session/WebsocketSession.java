package com.delight.gaia.websocket.session;

import com.delight.gaia.auth.subject.Subject;
import com.delight.gaia.base.session.RemoteSession;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;

public interface WebsocketSession extends RemoteSession {
    Subject getSubject();

    InetSocketAddress getRemoteAddress();

    URI getUri();

    Mono<Boolean> sendPong();

    Mono<Boolean> sendCustomPong();

    String getLanguage();


}
