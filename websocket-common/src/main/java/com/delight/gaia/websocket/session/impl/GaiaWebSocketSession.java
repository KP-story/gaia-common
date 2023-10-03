package com.delight.gaia.websocket.session.impl;

import com.delight.gaia.auth.subject.Subject;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageCodec;
import com.delight.gaia.base.session.SessionManager;
import com.delight.gaia.websocket.session.WebsocketSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Objects;

@Slf4j
public class GaiaWebSocketSession implements WebsocketSession {
    private final WebSocketSession delegate;
    private final Subject subject;
    private final SessionManager<WebsocketSession> sessionManager;
    private final Flux<WebSocketMessage> outputStream;

    private final Sinks.Many<WebSocketMessage> sink;
    private final String language;
    private final GaiaMessageCodec<WebSocketMessage, ? extends Class> gaiaMessageCodec;

    public GaiaWebSocketSession(WebSocketSession delegate, Subject subject, SessionManager sessionManager, String language, GaiaMessageCodec<WebSocketMessage, ? extends Class> gaiaMessageCodec) {
        this.delegate = delegate;
        this.subject = subject;
        this.sessionManager = sessionManager;
        this.language = language;
        this.gaiaMessageCodec = gaiaMessageCodec;
        sink = Sinks.many().unicast().onBackpressureBuffer();
        outputStream = sink.asFlux();
    }

    public Flux<WebSocketMessage> getOutputStream() {
        return outputStream;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return delegate.getHandshakeInfo().getRemoteAddress();
    }

    @Override
    public String getId() {
        return delegate.getId();
    }


    @Override
    public Mono<GaiaMessage> requestResponse(Mono<GaiaMessage> payloadMono) {
        return null;
    }

    @Override
    public Mono<Void> fireAndForget(Mono<GaiaMessage> payloadMono) {
        return payloadMono.map(gaiaMessage -> gaiaMessageCodec.encode(gaiaMessage)).flatMap(webSocketMessage -> {
            Sinks.EmitResult emitResult = sink.tryEmitNext(webSocketMessage);
            if (emitResult.isSuccess()) {
                return Mono.just(true).then();
            } else {
                log.error("sendPong error {}", emitResult);
                return Mono.error(new IOException(emitResult.toString()));
            }
        });
    }

    @Override
    public Mono<Void> fireAndForget(GaiaMessage request) {
        Sinks.EmitResult emitResult = sink.tryEmitNext(gaiaMessageCodec.encode(request));
        if (emitResult.isSuccess()) {
            return Mono.just(true).then();
        } else {
            log.error("sendPong error {}", emitResult);
            return Mono.error(new IOException(emitResult.toString()));
        }
    }


    @Override
    public URI getUri() {
        return delegate.getHandshakeInfo().getUri();
    }

    @Override
    public Mono<Boolean> sendPong() {
        try {
            Sinks.EmitResult emitResult = sink.tryEmitNext(delegate.pongMessage(DataBufferFactory::allocateBuffer));
            if (emitResult.isSuccess()) {
                return Mono.just(true);
            } else {
                log.error("sendPong error {}", emitResult);
                return Mono.just(false);
            }
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Boolean> sendCustomPong() {
        try {
            Sinks.EmitResult emitResult = sink.tryEmitNext(delegate.textMessage("1"));

            if (emitResult.isSuccess()) {
                return Mono.just(true);
            } else {
                log.error("sendPong error {}", emitResult);
                return Mono.just(false);
            }
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @Override
    public String getLanguage() {
        return language;
    }


    @Override
    public boolean isOpen() {
        return delegate.isOpen();
    }

    @Override
    public Subject getSubject() {
        return subject;
    }

    @Override
    public Mono<Void> close() {
        log.error("close ws {}", getId());
        sink.tryEmitComplete();
        return delegate.close();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GaiaWebSocketSession that = (GaiaWebSocketSession) o;
        return Objects.equals(getId(), that.getId());
    }

}
