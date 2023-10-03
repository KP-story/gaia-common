package com.delight.gaia.core.messaging.server.rsocket;

import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageCodec;
import com.delight.gaia.core.messaging.GaiaMessageExceptionHandler;
import com.delight.gaia.core.messaging.MessageHandler;
import com.delight.gaia.core.messaging.client.RSocketSession;
import io.netty.buffer.ByteBuf;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import reactor.core.publisher.Mono;

import java.util.Locale;

public class RsSocketSession extends RSocketSession implements RSocket {
    private final RSocket delegate;
    private final MessageHandler messageHandler;
    private final GaiaMessageExceptionHandler gaiaMessageExceptionHandler;

    public RsSocketSession(GaiaMessageCodec<Payload, ByteBuf> codec, RSocket delegate, MessageHandler messageHandler, GaiaMessageExceptionHandler gaiaMessageExceptionHandler) {
        super(codec);
        this.delegate = delegate;
        this.gaiaMessageExceptionHandler = gaiaMessageExceptionHandler;
        this.messageHandler = messageHandler;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean isOpen() {
        return !delegate.isDisposed();
    }

    @Override
    public Mono<GaiaMessage> requestResponse(Mono<GaiaMessage> request) {
        return decodePayload(buildPayload(request).flatMap(delegate::requestResponse));
    }

    @Override
    public Mono<Void> fireAndForget(Mono<GaiaMessage> request) {
        return buildPayload(request).flatMap(delegate::fireAndForget);
    }

    @Override
    public Mono<Void> fireAndForget(GaiaMessage request) {
        return null;
    }


    @Override
    public Mono<Void> close() {
        return null;
    }


    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        GaiaMessage gaiaMessage = codec.decode(payload);
        try {
            Mono<Void> handlerMono = messageHandler.handle(gaiaMessage, this);
            return handlerMono.doOnDiscard(Payload.class, this::releasePayload)
                    .doOnDiscard(GaiaMessage.class, gaiaMessage1 -> releaseMsg(gaiaMessage1)).doFinally(signalType -> {
                        releasePayload(payload);
                        releaseMsg(gaiaMessage);
                    });
        } catch (Exception e) {
            releasePayload(payload);
            releaseMsg(gaiaMessage);
            return Mono.error(e);
        }


    }

    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        GaiaMessage gaiaMessage = codec.decode(payload);
        try {
            Mono<GaiaMessage> handlerMono = messageHandler.handleReply(gaiaMessage, this);
            return buildPayload(handlerMono).doFinally(signalType -> {
                releasePayload(payload);
                releaseMsg(gaiaMessage);
            });
        } catch (Exception e) {
            releasePayload(payload);
            releaseMsg(gaiaMessage);
            return buildPayload(Mono.just(gaiaMessageExceptionHandler.handle(Locale.US, gaiaMessage, e)));
        }

    }


}
