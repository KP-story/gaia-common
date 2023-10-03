package vn.delight.gaia.concurrent.cache;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Getter
@Setter
@Accessors(chain = true)
public class CacheConfig {
    private Long ttl;
    private Long idleTime;
    private Long maxSize;
    private Function<Object, Mono<Object>> loader;
    private Function<Set<Object>, Flux<Map.Entry<Object, Object>>> multipleLoader;

}
