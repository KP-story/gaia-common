package com.delight.gaia.core.messaging;

import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import com.delight.gaia.web.exception.ExceptionHandler;
import com.delight.gaia.web.exception.GaiaWebExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class GaiaMessageExceptionHandler {
    private final GaiaWebExceptionHandler gaiaWebExceptionHandler;

    public GaiaMessage handle(Locale locale, GaiaMessage gaiaMessage, Throwable ex) {
        log.error("handle request ws {} exception ", gaiaMessage, ex);
        GaiaMessageResponse gaiaMessageResponse;
        try {
            gaiaMessageResponse = gaiaWebExceptionHandler.resolveHandler(ex).handle(locale, gaiaMessage, ex);
        } catch (Exception e) {
            log.error("handle message {} error ", gaiaMessage, e);
            gaiaMessageResponse = new GaiaMessageResponse(gaiaMessage, MessageCode.INTERNAL_SERVER_ERROR.getCode(), null);
        }
        return gaiaMessageResponse;
    }


}