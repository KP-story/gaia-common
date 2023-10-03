package com.delight.gaia.web.exception;

import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import com.delight.gaia.base.message.HttpErrorResponse;
import com.delight.gaia.base.message.ResponseBuilder;
import com.delight.gaia.base.vo.Error;
import com.delight.gaia.web.log.LogHttpRequestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Component
public class MethodArgumentNotValidExceptionHandler implements ExceptionHandler <MethodArgumentNotValidException>{
    private static final List ACCEPTED_EXCEPTION = List.of(MethodArgumentNotValidException.class);

    @Override
    public int getOrder() {
        return 10;
    }

    public HttpErrorResponse handle(Locale locale, ServerWebExchange exchange, MethodArgumentNotValidException e) {
         Map<String, Error> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getDefaultMessage() + error.getField(), new Error().setScope(error.getField()).setMessage(error.getDefaultMessage()));
        }
        return ResponseBuilder.error(  locale, MessageCode.INVALID_PARAMETER, errors.values().toArray(new Error[errors.size()]));
    }

    @Override
    public GaiaMessageResponse handle(Locale locale, GaiaMessage request, MethodArgumentNotValidException e) throws IOException {
        Map<String, Error> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getDefaultMessage() + error.getField(), new Error().setScope(error.getField()).setMessage(error.getDefaultMessage()));
        }
        return ResponseBuilder.errorGaiaMsg(locale,request,MessageCode.INVALID_PARAMETER, errors.values().toArray(new Error[errors.size()]));    }

    @Override
    public List<Class<? extends Throwable>> accept() {
        return ACCEPTED_EXCEPTION;
    }

}
