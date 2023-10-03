package com.delight.gaia.base.session;

import com.delight.gaia.base.message.GaiaMessageCodec;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SessionManager<T extends  RemoteSession> {
    void init();

    Mono<Boolean> register(T webSocketSession);

    Mono<Boolean> unregister(T webSocketSession);

    T getSession(String app, String sessionId);

    List<T> getUserSession(String app, Long userId);

    Map<String, List<T>> getUserSessions(String app, Collection<Long> userIds);

}
