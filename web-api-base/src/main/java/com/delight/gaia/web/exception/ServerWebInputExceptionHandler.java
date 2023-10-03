package com.delight.gaia.web.exception;

import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import com.delight.gaia.base.message.HttpErrorResponse;
import com.delight.gaia.base.message.ResponseBuilder;
import com.delight.gaia.base.vo.Error;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Component
public class ServerWebInputExceptionHandler implements ExceptionHandler<ServerWebInputException>{
    private static final List ACCEPTED_EXCEPTION = List.of(ServerWebInputException.class);

    @Override
    public HttpErrorResponse handle(Locale locale, ServerWebExchange exchange, ServerWebInputException ex) {
        Error error= new Error();
        error.setMessage(ex.getReason().split(":")[0]);
        error.setScope(ex.getMethodParameter().getParameterName());
        return ResponseBuilder.error(locale,MessageCode.BAD_REQUEST,error);
    }

    @Override
    public GaiaMessageResponse handle(Locale locale, GaiaMessage request, ServerWebInputException ex) throws IOException {
        Error error= new Error();
        error.setMessage(ex.getReason().split(":")[0]);
        error.setScope(ex.getMethodParameter().getParameterName());
        return ResponseBuilder.errorGaiaMsg(locale,request,MessageCode.BAD_REQUEST,error);
    }

    @Override
    public List<Class<? extends Throwable>> accept() {
        return ACCEPTED_EXCEPTION;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
