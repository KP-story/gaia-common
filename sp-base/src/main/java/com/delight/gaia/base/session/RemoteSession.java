package com.delight.gaia.base.session;

import com.delight.gaia.base.message.GaiaMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RemoteSession  {


    /**
     * Return a unique session identifier.
     */
    String getId();

    boolean isOpen();

    Mono<GaiaMessage> requestResponse(Mono<GaiaMessage> request);

    Mono<Void> fireAndForget(Mono<GaiaMessage> request);
    Mono<Void> fireAndForget(GaiaMessage request);



    Mono<Void> close();


}
