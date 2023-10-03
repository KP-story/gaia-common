package com.delight.gaia.web.exception;


import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.exception.AuthorizationException;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import com.delight.gaia.base.message.HttpErrorResponse;
import com.delight.gaia.base.message.ResponseBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Component
public class AuthorizationExceptionController implements ExceptionHandler<AuthorizationException> {
    private static final List ACCEPTED_EXCEPTION = List.of(AuthorizationException.class);

    @Override
    public HttpErrorResponse handle(Locale locale, ServerWebExchange exchange, AuthorizationException ex) {
        return ResponseBuilder.error(locale,MessageCode.PERMISSION_DENIED);
    }

    @Override
    public GaiaMessageResponse handle(Locale locale,GaiaMessage request, AuthorizationException ex) throws IOException {
        return ResponseBuilder.errorGaiaMsg(locale,request,MessageCode.PERMISSION_DENIED);
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
