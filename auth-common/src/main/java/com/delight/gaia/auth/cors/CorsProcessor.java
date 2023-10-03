package com.delight.gaia.auth.cors;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface CorsProcessor {
    Mono<Boolean> process(ServerWebExchange exchange);
}
