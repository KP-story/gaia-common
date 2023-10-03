package com.delight.gaia.web.exception;

import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import com.delight.gaia.base.message.HttpErrorResponse;
import com.delight.gaia.base.message.ResponseBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Component
public class UnsupportedMediaTypeStatusExceptionHandler implements ExceptionHandler<UnsupportedMediaTypeStatusException>{
    private static final List ACCEPTED_EXCEPTION = List.of(UnsupportedMediaTypeStatusException.class);

    @Override
    public HttpErrorResponse handle(Locale locale, ServerWebExchange exchange, UnsupportedMediaTypeStatusException ex) {

        return ResponseBuilder.error(locale,MessageCode.NOT_SUPPORT_MEDIA_TYPE,ex.getContentType().toString());
    }

    @Override
    public GaiaMessageResponse handle(Locale locale, GaiaMessage request, UnsupportedMediaTypeStatusException ex) throws IOException {
        return ResponseBuilder.errorGaiaMsg(locale,request,MessageCode.NOT_SUPPORT_MEDIA_TYPE,ex.getContentType().toString());
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
