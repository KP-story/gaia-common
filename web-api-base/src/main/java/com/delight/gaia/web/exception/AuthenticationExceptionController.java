package com.delight.gaia.web.exception;


import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.exception.AuthenticationException;
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
public class AuthenticationExceptionController implements ExceptionHandler<AuthenticationException> {
    private static final List ACCEPTED_EXCEPTION = List.of(AuthenticationException.class);


    @Override
    public HttpErrorResponse handle(Locale locale, ServerWebExchange exchange, AuthenticationException ex) {
        return ResponseBuilder.error(locale,MessageCode.UNAUTHORIZED);
    }

    @Override
    public GaiaMessageResponse handle(Locale locale,GaiaMessage request, AuthenticationException ex) throws IOException {
        return ResponseBuilder.errorGaiaMsg(locale,request,MessageCode.UNAUTHORIZED);
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
