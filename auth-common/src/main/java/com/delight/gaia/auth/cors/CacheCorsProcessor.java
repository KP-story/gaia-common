package com.delight.gaia.auth.cors;

import com.delight.gaia.auth.context.Realm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class CacheCorsProcessor implements CorsProcessor {

    private Realm realm;



    @Override
    public Mono<Boolean> process(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders responseHeaders = response.getHeaders();
        if (!CorsUtils.isCorsRequest(request)) {
            return Mono.just(true);
        }
        if (responseHeaders.getFirst(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN) != null) {
            log.trace("Skip: response already contains \"Access-Control-Allow-Origin\"");
            return Mono.just(true);
        }
        return handleInternal(exchange).doOnSuccess(isAllowed -> {
            if (isAllowed) {
                responseHeaders.setAccessControlAllowHeaders(realm.allowHeaders(request.getHeaders().getOrigin()));
                responseHeaders.setAccessControlAllowCredentials(true);
                responseHeaders.setAccessControlMaxAge(60000);
                responseHeaders.setAccessControlAllowOrigin(request.getHeaders().getOrigin());
                responseHeaders.setAccessControlAllowMethods(Arrays.asList(HttpMethod.DELETE, HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.OPTIONS, HttpMethod.PATCH));
            } else {
                rejectRequest(response);
            }
        });
    }

    /**
     * Invoked when one of the CORS checks failed.
     */

    protected void rejectRequest(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
    }

    /**
     * Handle the given request.
     */
    protected Mono<Boolean> handleInternal(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String requestOrigin = request.getHeaders().getOrigin();
        return realm.allowDomain(requestOrigin);
    }

}
