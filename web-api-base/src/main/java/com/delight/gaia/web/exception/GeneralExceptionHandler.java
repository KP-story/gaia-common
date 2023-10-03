package com.delight.gaia.web.exception;

import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.exception.CommandFailureException;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import com.delight.gaia.base.message.HttpErrorResponse;
import com.delight.gaia.base.message.ResponseBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Component
public class GeneralExceptionHandler implements ExceptionHandler<Throwable> {
    private static final List ACCEPTED_EXCEPTION = List.of(Throwable.class);

    @Override
    public int getOrder() {
        return 0;
    }

    public HttpErrorResponse handle(Locale locale, ServerWebExchange exchange, Throwable ex) {
        return ResponseBuilder.error(locale,MessageCode.INTERNAL_SERVER_ERROR);
    }

    @Override
    public GaiaMessageResponse handle(Locale locale, GaiaMessage request, Throwable ex) throws IOException {
        return ResponseBuilder.errorGaiaMsg(locale,request,MessageCode.INTERNAL_SERVER_ERROR);
    }

    @Override
    public List<Class<? extends Throwable>> accept() {
        return ACCEPTED_EXCEPTION;
    }
}
