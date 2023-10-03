package com.delight.gaia.websocket.config;

import com.delight.gaia.auth.context.GaiaSecurityManager;
import com.delight.gaia.base.message.GaiaMessageCodec;
import com.delight.gaia.base.session.SessionManager;
import com.delight.gaia.websocket.codec.ProtoWebsocketCodec;
import com.delight.gaia.websocket.handler.GaiaWebsocketHandler;
import com.delight.gaia.websocket.handler.MessageListener;
import com.delight.gaia.websocket.session.impl.CacheSessionManager;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SessionConfig.class)
@ConditionalOnProperty(name = "websocket", matchIfMissing = false)
public class WebsocketAutoConfiguration {
    @Bean
    public GaiaMessageCodec<WebSocketMessage, ByteString> websocketCodec(SessionConfig sessionConfig) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class codec = sessionConfig.getCodec();
        if (codec == null) {
            return new ProtoWebsocketCodec();
        }
        Object newInstance = codec.getDeclaredConstructor().newInstance();
        return (GaiaMessageCodec) newInstance;
    }

    @Bean
    public SessionManager sessionManager(SessionConfig sessionConfig) {
        CacheSessionManager cacheSessionManager = new CacheSessionManager(sessionConfig);
        cacheSessionManager.init();
        return cacheSessionManager;
    }


    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public HandlerMapping handlerMapping(WebSocketHandler handler, SessionConfig sessionConfig) {
        Map<String, WebSocketHandler> handlerMap = Map.of(
                sessionConfig.getEndpoint(), handler
        );

        return new SimpleUrlHandlerMapping(handlerMap, 1);
    }

    @Bean
    public WebSocketHandler webSocketHandler(SessionManager sessionManager, MessageListener messageListener, GaiaSecurityManager gaiaSecurityManager, @Qualifier("websocketCodec") GaiaMessageCodec<WebSocketMessage, ByteString> websocketCodec) {
        return new GaiaWebsocketHandler(sessionManager, messageListener, gaiaSecurityManager,websocketCodec);
    }
}
