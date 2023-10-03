package com.delight.gaia.web.exception;

import com.delight.gaia.base.message.HttpErrorResponse;
import com.delight.gaia.web.log.LogHttpRequestBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
@Order(-2)

public class GaiaWebExceptionHandler implements WebExceptionHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final List<ExceptionHandler> exceptionHandlers;
    private Map<Class, ExceptionHandler> mappedHandlers = new HashMap<>();

    @PostConstruct
    public void init() {
        for (ExceptionHandler exceptionHandler : exceptionHandlers) {
            List<Class<? extends Throwable>> accept = exceptionHandler.accept();
            for (Class c : accept) {
                addExceptionMapping(c, exceptionHandler);
            }
        }

    }

    public ExceptionHandler resolveHandler(Throwable exception) {
        ExceptionHandler exceptionHandler = mappedHandlers.get(exception.getClass());
        if (exceptionHandler == null) {
            Throwable cause = exception.getCause();
            if (cause != null) {
                return resolveHandler(cause);
            } else {
                return mappedHandlers.get(Throwable.class);
            }
        }
        return exceptionHandler;
    }

    private void addExceptionMapping(Class<? extends Throwable> exceptionType, ExceptionHandler exceptionHandler) {
        this.mappedHandlers.put(exceptionType, exceptionHandler);
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("handle request {} exception ", LogHttpRequestBuilder.build(exchange.getRequest()), ex);
        HttpErrorResponse errorResponse = resolveHandler(ex).handle(exchange.getLocaleContext().getLocale(),exchange, ex);
        return buildErrorJson(exchange, errorResponse);
    }


    public Mono<Void> buildErrorJson(ServerWebExchange serverWebExchange, HttpErrorResponse httpErrorResponse) {
        DataBufferFactory bufferFactory = serverWebExchange.getResponse().bufferFactory();
        serverWebExchange.getResponse().setStatusCode(httpErrorResponse.getStatusCode());
        DataBuffer dataBuffer = null;
        try {
            dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(httpErrorResponse.getBody()));
        } catch (JsonProcessingException e) {
            dataBuffer = bufferFactory.wrap("Encoder error ".getBytes());
        }
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

}
