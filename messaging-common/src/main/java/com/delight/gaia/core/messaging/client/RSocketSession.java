package com.delight.gaia.core.messaging.client;

import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageCodec;
import com.delight.gaia.base.message.GaiaMessageUtils;
import com.delight.gaia.base.session.RemoteSession;
import io.netty.buffer.ByteBuf;
import io.rsocket.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public abstract class RSocketSession implements RemoteSession {
    protected final GaiaMessageCodec<Payload, ByteBuf> codec;

    protected Mono<Payload> buildPayload(Mono<GaiaMessage> request) {
        return request.map(codec::encode).doOnDiscard(Payload.class, this::releasePayload)
                .doOnDiscard(GaiaMessage.class, gaiaMessage -> releaseMsg(gaiaMessage));
    }

    protected void releaseMsg(GaiaMessage gaiaMessage) {

            try {
                Object rawBody = GaiaMessageUtils._getRawBody(gaiaMessage);
                if (rawBody != null && rawBody instanceof ByteBuffer) {
                    ByteBuf byteBuf = (ByteBuf) rawBody;
                    if (byteBuf.refCnt() > 0) {
                        byteBuf.release();
                    }
                }

            } catch (Exception e) {
                log.error("release failed ", e);
            }

    }

    protected void releasePayload(Payload payload) {
        try {
            if (payload.refCnt() >0) {
                payload.release();
            }
        } catch (Exception e) {
            log.error("release failed ", e);
        }
    }

    protected Mono decodePayload(Mono<Payload> response) {
        return response.map(payload -> {
            try {
                return codec.decode(payload);
            } finally {
                releasePayload(payload);
            }
        }).doOnDiscard(Payload.class, this::releasePayload)
                .doOnDiscard(GaiaMessage.class, gaiaMessage -> releaseMsg(gaiaMessage));

    }
}
