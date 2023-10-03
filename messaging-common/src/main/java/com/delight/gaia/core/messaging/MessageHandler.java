package com.delight.gaia.core.messaging;

import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.session.RemoteSession;
import reactor.core.publisher.Mono;

public interface MessageHandler {
    Mono<GaiaMessage> handleReply(GaiaMessage gaiaMessage, RemoteSession remoteSession) throws Exception;

    Mono<Void> handle(GaiaMessage gaiaMessage, RemoteSession remoteSession)throws Exception;

}
