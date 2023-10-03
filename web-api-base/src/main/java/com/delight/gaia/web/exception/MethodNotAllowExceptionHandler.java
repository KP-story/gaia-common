package com.delight.gaia.web.exception;

import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import com.delight.gaia.base.message.HttpErrorResponse;
import com.delight.gaia.base.message.ResponseBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Component
public class MethodNotAllowExceptionHandler implements ExceptionHandler<MethodNotAllowedException> {
    private static final List ACCEPTED_EXCEPTION = List.of(MethodNotAllowedException.class);

    @Override
    public HttpErrorResponse handle(Locale locale, ServerWebExchange exchange, MethodNotAllowedException ex) {
        return ResponseBuilder.error(locale,MessageCode.NOT_SUPPORT_METHOD, ex.getHttpMethod());
    }

    @Override
    public GaiaMessageResponse handle(Locale locale, GaiaMessage request, MethodNotAllowedException ex) throws IOException {
        return ResponseBuilder.errorGaiaMsg(locale,request,MessageCode.NOT_SUPPORT_METHOD, ex.getHttpMethod());
    }

    @Override
    public List<Class<? extends Throwable>> accept() {
        return ACCEPTED_EXCEPTION;
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
