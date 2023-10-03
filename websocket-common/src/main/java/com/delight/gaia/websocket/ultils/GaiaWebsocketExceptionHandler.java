package com.delight.gaia.websocket.ultils;

import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import com.delight.gaia.web.exception.ExceptionHandler;
import com.delight.gaia.web.exception.GaiaWebExceptionHandler;
import com.delight.gaia.websocket.session.WebsocketSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;

@Slf4j
@Component
public class GaiaWebsocketExceptionHandler extends GaiaWebExceptionHandler {
    public GaiaWebsocketExceptionHandler(List<ExceptionHandler> exceptionHandlers) {
        super(exceptionHandlers);
    }

    public Mono<Void> handle( WebsocketSession websocketSession, GaiaMessage gaiaMessage, Throwable ex) {
        log.error("handle request ws {} exception ", gaiaMessage, ex);
        GaiaMessageResponse gaiaMessageResponse;
        try {
            gaiaMessageResponse = resolveHandler(ex).handle(Locale.forLanguageTag(websocketSession.getLanguage()),gaiaMessage, ex);
        } catch (Exception e) {
            log.error("handle message {} error ", gaiaMessage, e);
            gaiaMessageResponse = new GaiaMessageResponse(gaiaMessage, MessageCode.INTERNAL_SERVER_ERROR.getCode(), MessageCode.INTERNAL_SERVER_ERROR.getMessage());
        }
        return websocketSession.fireAndForget(Mono.just(gaiaMessageResponse));
    }


}
