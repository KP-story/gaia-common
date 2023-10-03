package com.delight.gaia.web.exception;


import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import com.delight.gaia.base.message.HttpErrorResponse;
import com.delight.gaia.base.message.ResponseBuilder;
import com.delight.gaia.base.vo.Error;
import com.delight.gaia.base.vo.ResultCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class AResponseStatusExceptionHandler implements ExceptionHandler<ResponseStatusException> {
    private static final List ACCEPTED_EXCEPTION = List.of(ResponseStatusException.class);

    @Override
    public HttpErrorResponse handle(Locale locale,ServerWebExchange exchange, ResponseStatusException ex) {
        ResultCode resultCode;
        if (ex.getStatusCode().value() == NOT_FOUND.value()) {
            resultCode = MessageCode.NOT_FOUND;
        } else {
            resultCode = MessageCode.INTERNAL_SERVER_ERROR;
        }
        Error error = new Error().setCode(ex.getStatusCode().value()).setMessage(ex.getReason()).setScope(exchange.getRequest().getURI().toString());
        return ResponseBuilder.error(locale,resultCode, error);
    }

    @Override
    public GaiaMessageResponse handle(Locale locale,GaiaMessage request, ResponseStatusException ex) throws IOException {
        ResultCode resultCode;
        if (ex.getStatusCode().value() == NOT_FOUND.value()) {
            resultCode = MessageCode.NOT_FOUND;
        } else {
            resultCode = MessageCode.INTERNAL_SERVER_ERROR;
        }
        Error error = new Error().setCode(ex.getStatusCode().value()).setMessage(ex.getReason());

        return ResponseBuilder.errorGaiaMsg(locale,request,resultCode, error);
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
