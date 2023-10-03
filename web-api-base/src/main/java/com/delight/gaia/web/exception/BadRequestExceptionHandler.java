package com.delight.gaia.web.exception;

import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import com.delight.gaia.base.message.HttpErrorResponse;
import com.delight.gaia.base.message.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


@Component
public class BadRequestExceptionHandler implements ExceptionHandler<Exception> {
    private static final List ACCEPTED_EXCEPTION = List.of(HttpMessageNotReadableException.class);

    @Override
    public int getOrder() {
        return 10;
    }

    @Override
    public HttpErrorResponse handle(Locale locale, ServerWebExchange exchange, Exception ex) {
        return ResponseBuilder.error(locale,MessageCode.BAD_REQUEST);
    }

    @Override
    public GaiaMessageResponse handle(Locale locale,GaiaMessage request, Exception ex) throws IOException {
        return ResponseBuilder.errorGaiaMsg(locale,request,MessageCode.BAD_REQUEST);
    }

    @Override
    public List<Class<? extends Throwable>> accept() {
        return ACCEPTED_EXCEPTION;
    }


}
