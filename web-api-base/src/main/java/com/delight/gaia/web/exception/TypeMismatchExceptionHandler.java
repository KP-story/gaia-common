package com.delight.gaia.web.exception;

import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import com.delight.gaia.base.message.HttpErrorResponse;
import com.delight.gaia.base.message.ResponseBuilder;
import com.delight.gaia.web.log.LogHttpRequestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


@Component
public class TypeMismatchExceptionHandler implements ExceptionHandler<MethodArgumentTypeMismatchException>{
    private static final List ACCEPTED_EXCEPTION = List.of(MethodArgumentTypeMismatchException.class);

    @Override
    public int getOrder() {
        return 10;
    }

    public HttpErrorResponse handle(Locale locale, ServerWebExchange exchange, MethodArgumentTypeMismatchException e) {
        String parameter = e.getParameter().getParameterName();
        String typeExpected = e.getParameter().getParameterType().getSimpleName();
        return ResponseBuilder.error(locale,MessageCode.TYPE_MISMATCH, parameter, typeExpected);
    }

    @Override
    public GaiaMessageResponse handle(Locale locale, GaiaMessage request, MethodArgumentTypeMismatchException e) throws IOException {
        String parameter = e.getParameter().getParameterName();
        String typeExpected = e.getParameter().getParameterType().getSimpleName();
        return ResponseBuilder.errorGaiaMsg(locale,request,MessageCode.TYPE_MISMATCH, parameter, typeExpected);    }

    @Override
    public List<Class<? extends Throwable>> accept() {
        return ACCEPTED_EXCEPTION;
    }
}
