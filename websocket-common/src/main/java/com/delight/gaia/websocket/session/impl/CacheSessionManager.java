package com.delight.gaia.websocket.session.impl;

import com.delight.gaia.base.constant.AccountType;
import com.delight.gaia.base.message.GaiaMessageCodec;
import com.delight.gaia.base.session.SessionManager;
import com.delight.gaia.websocket.config.SessionConfig;
import com.delight.gaia.websocket.session.WebsocketSession;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.Scheduler;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CacheSessionManager implements SessionManager<WebsocketSession> {

    private Cache<String, WebsocketSession> sessionCache;
    private final AtomicBoolean isOpen = new AtomicBoolean(false);
    private Cache<String, List<WebsocketSession>> principleSessionCache;
    private final SessionConfig sessionConfig;

    private void buildPrincipleCache() {
        Caffeine principleSessionCacheBuilder = Caffeine.newBuilder().scheduler(Scheduler.systemScheduler());
        principleSessionCache = principleSessionCacheBuilder.build();
    }

    private void buildSessionCache() {
        Caffeine sessionCacheBuilder = Caffeine.newBuilder();
        if (sessionConfig.getConnectionIdleTime() != null) {
            sessionCacheBuilder = sessionCacheBuilder.expireAfterAccess(sessionConfig.getConnectionIdleTime(), TimeUnit.MILLISECONDS);
        }
        if (sessionConfig.getConnectionTTL() != null) {
            sessionCacheBuilder = sessionCacheBuilder.expireAfterWrite(sessionConfig.getConnectionTTL(), TimeUnit.MILLISECONDS);
        }
        sessionCacheBuilder.removalListener((key, value, cause) -> {
            WebsocketSession tWebSocketSession = (WebsocketSession) value;
            if (cause == RemovalCause.EXPIRED || cause == RemovalCause.SIZE) {
                log.error("websocket expired {} cause ", tWebSocketSession, cause);
                unregister(tWebSocketSession).subscribe();
            }
        });
        sessionCacheBuilder.scheduler(Scheduler.systemScheduler());
        sessionCache = sessionCacheBuilder.build();
    }

    String getSessionKey(String app, String sessionId) {
        return app + ":" + sessionId;
    }

    String getUserKey(String app, Long userId) {
        return app + ":" + userId;
    }

    @Override
    public void init() {
        if (isOpen.compareAndSet(false, true)) {
            buildPrincipleCache();
            buildSessionCache();
        }
    }


    @Override
    public Mono<Boolean> register(WebsocketSession webSocketSession) {
        if(!webSocketSession.isOpen())
        {
            return Mono.just(false);
        }
        AtomicBoolean add = new AtomicBoolean(true);
        sessionCache.asMap().compute(getSessionKey(webSocketSession.getSubject().getClientInfo().getApp(), webSocketSession.getId()), (s, remoteSession) -> {
            if (remoteSession != null) {
                log.error("duplicate session id {} ", webSocketSession.getId());
                add.set(false);
                return remoteSession;
            } else {
                if (sessionCache.estimatedSize() > sessionConfig.getMaxConnection()) {
                    log.error("reach limit connection per note, < {}  ", sessionCache.estimatedSize());
                    add.set(false);
                    return null;
                } else {
                    if (webSocketSession.getSubject() == null || webSocketSession.getSubject().getInfo().getAccountType().equals(AccountType.ANONYMOUS)) {
                        log.error("not found subject  ");
                        add.set(false);
                        return null;
                    }
                    return webSocketSession;

                }
            }
        });
        if (add.get()) {
            principleSessionCache.asMap().compute(getUserKey(webSocketSession.getSubject().getClientInfo().getApp(), webSocketSession.getSubject().getId()), (userId, websocketSessions) -> {
                if (websocketSessions == null) {
                    websocketSessions = new ArrayList<>(1);
                }
                websocketSessions.add(webSocketSession);
                return websocketSessions;
            });

            return Mono.just(true);
        } else {
            return webSocketSession.close().thenReturn(false);

        }
    }

    @Override
    public Mono<Boolean> unregister(WebsocketSession webSocketSession) {
        log.error("unregister {} ", webSocketSession.getId());
        Long userId = webSocketSession.getSubject().getId();
        sessionCache.invalidate(getSessionKey(webSocketSession.getSubject().getClientInfo().getApp(), webSocketSession.getId()));
        principleSessionCache.asMap().compute(getUserKey(webSocketSession.getSubject().getClientInfo().getApp(),userId), (aLong, websocketSessions) -> {
            if (websocketSessions == null) {
                return null;
            } else {
                websocketSessions.remove(webSocketSession);
            }
            return websocketSessions;
        });
        return webSocketSession.close().thenReturn(true);
    }

    @Override
    public WebsocketSession getSession(String app,String sessionId) {
        return sessionCache.getIfPresent(getSessionKey(app,sessionId));
    }

    @Override
    public List<WebsocketSession> getUserSession(String app, Long userId) {
        return principleSessionCache.getIfPresent(getUserKey(app,userId));
    }

    @Override
    public Map<String, List<WebsocketSession>> getUserSessions(String app, Collection<Long> userIds) {
        return principleSessionCache.getAllPresent(userIds.stream().map(userId->getUserKey(app,userId)).collect(Collectors.toList()));
    }


}
