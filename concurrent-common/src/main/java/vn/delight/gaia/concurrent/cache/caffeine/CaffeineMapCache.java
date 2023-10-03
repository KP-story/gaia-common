package vn.delight.gaia.concurrent.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.delight.gaia.concurrent.cache.KMapCache;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor
public class CaffeineMapCache<K, V> implements KMapCache<K, V> {
    private final Cache<K, V> cache;
    private final Function<K, Mono<V>> loader;
    private final Function<Set<K>, Flux<Map.Entry<K, V>>> multipleLoader;


    @Override
    public Flux<Map.Entry<K, V>> getMultiple(Set<K> keys) {
        Map<K, V> present = cache.getAllPresent(keys);
        Set<Map.Entry<K, V>> entries = present.entrySet();
        Flux<Map.Entry<K, V>> entryFlux = Flux.fromIterable(entries);
        if (present.size() < keys.size()) {
            Set<K> missingKeys = new HashSet<>(keys.size() - present.size());
            for (K key : keys) {
                if (!present.containsKey(key)) {
                    missingKeys.add(key);
                }

            }
            if (multipleLoader != null) {
                return Flux.merge(multipleLoader.apply(missingKeys).doOnNext(kvEntry -> cache.put(kvEntry.getKey(), kvEntry.getValue())), entryFlux);
            }
        }
        return entryFlux;
    }

    @Override
    public Mono<V> get(K key) {
        V value = cache.getIfPresent(key);
        if (value == null && loader != null) {
            if (value == null) {
                return loader.apply(key).doOnSuccess(v -> {
                    if (v != null)
                        cache.put(key, v);

                });
            } else {
                return Mono.just(value);
            }

        } else {
            return Mono.just(value);
        }
    }

    @Override
    public Mono<V> putIfAbsent(K key, V value, long ttl) {
        cache.put(key, value);
        return Mono.just(value);
    }

    @Override
    public Mono<V> putIfAbsent(K key, V value, long ttl, long maxIdleTime) {
        cache.put(key, value);
        return Mono.just(value);
    }

    @Override
    public Mono<V> put(K key, V value, long ttl) {
        cache.put(key, value);
        return Mono.just(value);
    }

    @Override
    public Mono<V> put(K key, V value, long ttl, long maxIdleTime) {
        return Mono.just(value);
    }

    @Override
    public Mono<Boolean> fastPut(K key, V value, long ttl) {
        cache.put(key, value);
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> fastPut(K key, V value, long ttl, long maxIdleTime) {
        cache.put(key, value);
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> fastPutIfAbsent(K key, V value, long ttl, long maxIdleTime) {
        cache.asMap().putIfAbsent(key, value);
        return Mono.just(true);
    }

    @Override
    public Mono<Integer> size() {
        return Mono.just(Long.valueOf(cache.estimatedSize()).intValue());
    }

    @Override
    public Mono<V> compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return Mono.just(cache.asMap().compute(key, remappingFunction));
    }

    @Override
    public Mono<V> computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return Mono.just(cache.asMap().computeIfAbsent(key, mappingFunction));
    }

    @Override
    public Mono<V> computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return Mono.just(cache.asMap().computeIfPresent(key, remappingFunction));
    }

    @Override
    public Mono<Map<K, V>> getAll(Set<K> keys) {
        return Mono.just(cache.asMap());
    }

    @Override
    public Mono<Void> putAll(Map<? extends K, ? extends V> map) {
        cache.putAll(map);
        return Mono.empty();
    }


    @Override
    public Mono<Boolean> containsKey(Object key) {
        return Mono.just(cache.asMap().containsKey(key));
    }

    @Override
    public Mono<Long> fastRemove(K... keys) {
        cache.invalidateAll(Arrays.asList(keys));
        return Mono.just(Long.valueOf(keys.length));
    }

    @Override
    public Mono<Boolean> fastRemove(K key) {
        cache.invalidate(key);
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> fastPut(K key, V value) {
        cache.put(key, value);
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> fastPutIfAbsent(K key, V value) {
        V a = cache.asMap().putIfAbsent(key, value);
        return Mono.just(a == null);
    }

    @Override
    public Mono<Set<K>> readAllKeySet() {
        return Mono.just(cache.asMap().keySet());
    }

    @Override
    public Mono<Collection<V>> readAllValues() {
        return Mono.just(cache.asMap().values());
    }

    @Override
    public Mono<Set<Map.Entry<K, V>>> readAllEntrySet() {
        return Mono.just(cache.asMap().entrySet());
    }

    @Override
    public Mono<Map<K, V>> readAllMap() {
        return Mono.just(cache.asMap());
    }


    @Override
    public Mono<V> put(K key, V value) {
        cache.put(key, value);
        return Mono.just(value);
    }

    @Override
    public Mono<V> remove(K key) {
        V v = cache.getIfPresent(key);
        cache.invalidate(key);
        return Mono.just(v);
    }


    @Override
    public Mono<V> putIfAbsent(K key, V value) {
        return Mono.just(cache.asMap().put(key, value));
    }

}
