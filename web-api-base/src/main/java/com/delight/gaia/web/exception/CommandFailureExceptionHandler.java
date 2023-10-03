package com.delight.gaia.web.exception;

import com.delight.gaia.base.exception.CommandFailureException;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import com.delight.gaia.base.message.HttpErrorResponse;
import com.delight.gaia.base.message.ResponseBuilder;
import com.delight.gaia.base.vo.ResultCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


@Component
public class CommandFailureExceptionHandler implements ExceptionHandler<CommandFailureException> {
    private static final List ACCEPTED_EXCEPTION = List.of(CommandFailureException.class);

    @Override
    public int getOrder() {
        return 10;
    }

    public HttpErrorResponse handle(Locale locale, ServerWebExchange exchange, CommandFailureException e) {
        ResultCode resultCode = e.getResultCode();
        return ResponseBuilder.error(locale,resultCode, e.getArgs());
    }

    @Override
    public GaiaMessageResponse handle(Locale locale,GaiaMessage request, CommandFailureException e) throws IOException {
        ResultCode resultCode = e.getResultCode();
        return ResponseBuilder.errorGaiaMsg(locale,request,resultCode, e.getArgs());
    }

    @Override
    public List<Class<? extends Throwable>> accept() {
        return ACCEPTED_EXCEPTION;
    }
}
