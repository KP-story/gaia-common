package com.delight.gaia.auth.cors;

import lombok.AllArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TripiCorsFilter implements WebFilter {
    private CorsProcessor processor;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        return this.processor.process(exchange).flatMap(isAllowed -> {
                    if (!isAllowed || CorsUtils.isPreFlightRequest(request)) {
                        return Mono.empty();
                    } else {
                        return chain.filter(exchange);
                    }
                }
        );
    }
}
