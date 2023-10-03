package com.delight.gaia.web.exception;

import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import com.delight.gaia.base.message.HttpErrorResponse;
import com.delight.gaia.base.message.ResponseBuilder;
import com.delight.gaia.base.vo.Error;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


@Component
public class InvalidJsonMappingExceptionHandler implements ExceptionHandler<JsonMappingException> {
    private static final List ACCEPTED_EXCEPTION = List.of(JsonMappingException.class);

    @Override
    public int getOrder() {
        return 10;
    }

    public HttpErrorResponse handle(Locale locale, ServerWebExchange exchange, JsonMappingException e) {
        Error[] error = new Error[e.getPath().size()];
        int i = 0;
        for (JsonMappingException.Reference reference : e.getPath()) {
            error[i] = new Error().setScope(reference.getFieldName()).setMessage(reference.getDescription());
            i++;
        }
        return ResponseBuilder.error(locale,MessageCode.BAD_REQUEST, error);
    }

    @Override
    public GaiaMessageResponse handle(Locale locale, GaiaMessage request, JsonMappingException e) throws IOException {
        Error[] error = new Error[e.getPath().size()];
        int i = 0;
        for (JsonMappingException.Reference reference : e.getPath()) {
            error[i] = new Error().setScope(reference.getFieldName()).setMessage(reference.getDescription());
            i++;
        }
        return ResponseBuilder.errorGaiaMsg(locale,request,MessageCode.BAD_REQUEST, error);
    }

    @Override
    public List<Class<? extends Throwable>> accept() {
        return ACCEPTED_EXCEPTION;
    }
}
