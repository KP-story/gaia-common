package com.delight.gaia.websocket.handler;

import com.delight.gaia.auth.context.GaiaSecurityManager;
import com.delight.gaia.auth.subject.ClientInfo;
import com.delight.gaia.auth.token.JwtToken;
import com.delight.gaia.auth.token.Token;
import com.delight.gaia.base.message.GaiaMessageCodec;
import com.delight.gaia.base.session.SessionManager;
import com.delight.gaia.base.vo.Pair;
import com.delight.gaia.websocket.session.WebsocketSession;
import com.delight.gaia.websocket.session.impl.GaiaWebSocketSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

@Slf4j
@RequiredArgsConstructor
public class GaiaWebsocketHandler implements WebSocketHandler {
    private final SessionManager<WebsocketSession> sessionManager;
    private final MessageListener messageListener;
    private final GaiaSecurityManager securityManager;
    private final GaiaMessageCodec websocketCodec;

    @Autowired(required = false)
    private WebsocketEventListener websocketEventListener;

    public Pair<Token, String> detectToken(URI uri) throws UnsupportedEncodingException {
        String query = uri.getRawQuery();
        String[] pairs = query.split("&");
        Pair<Token, String> data = new Pair<>();
        for (String pair : pairs) {
            if (pair.startsWith("token")) {
                int idx = pair.indexOf("=");
                data.setLeft(new JwtToken().setToken(URLDecoder.decode(pair.substring(idx + 1), "UTF-8")));
            } else if (pair.startsWith("device-id")) {
                int idx = pair.indexOf("=");
                data.setRight(URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }

        }
        return data;
    }
    @SneakyThrows
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Pair<Token, String> tokenStringPair = detectToken(session.getHandshakeInfo().getUri());
        return securityManager.verify(tokenStringPair.getLeft(), new ClientInfo().setDeviceId(tokenStringPair.getRight())).map(subject -> {
            subject.getClientInfo().setDeviceId(tokenStringPair.getRight());
            HandshakeInfo handshakeInfo = session.getHandshakeInfo();
            String lang = handshakeInfo.getHeaders().getFirst(HttpHeaders.ACCEPT_LANGUAGE);
            if(lang==null)lang="en";
            GaiaWebSocketSession gaiaWebSocketSession = new GaiaWebSocketSession(session, subject, sessionManager, lang, websocketCodec);
            return gaiaWebSocketSession;
        }).flatMap(gaiaWebSocketSession -> sessionManager.register(gaiaWebSocketSession).doOnSuccess(accept -> {
                    if (accept && websocketEventListener != null) {
                        websocketEventListener.onConnect(gaiaWebSocketSession).subscribe();
                    }
                })
                .flatMap(register -> register ? Mono.zip(session.receive().doOnNext(WebSocketMessage::retain).concatMap(webSocketMessage ->
                                {
                                    try {
                                            if (webSocketMessage.getPayload().capacity() == 1) {
                                                return gaiaWebSocketSession.sendCustomPong();
                                            }
                                            return messageListener.onMessage(websocketCodec.decode(webSocketMessage), gaiaWebSocketSession);

                                    } catch (Exception e) {
                                        return Mono.error(e);
                                    }
                                }
                        ).doOnEach(voidSignal -> {
                            sessionManager.getSession(gaiaWebSocketSession.getSubject().getClientInfo().getApp(), session.getId());
                        }).then(), session.send(gaiaWebSocketSession.getOutputStream())).then()
                        .doFinally(signalType -> {
                            if (websocketEventListener != null) {
                                websocketEventListener.onDisconnect(gaiaWebSocketSession).subscribe();
                            }
                            sessionManager.unregister(gaiaWebSocketSession).publishOn(Schedulers.boundedElastic()).subscribe();
                        }) : Mono.empty())).doOnError(throwable -> log.error("handle websocket {} error", session.getId(), throwable));

    }


}
