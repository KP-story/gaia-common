package vn.delight.gaia.concurrent.cache;

import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;


public class CacheService<K, V> {
    protected KMapCache<K, V> mapCache;
    private final CacheManager cacheManager;
    private String name;
    @Setter
    private CacheConfig cacheConfig;

    public CacheService(CacheManager cacheManager, String name) {
        this.cacheManager = cacheManager;
        this.name = name;

    }

    protected void init() {
        mapCache = cacheManager.getCache(name, cacheConfig);
    }


    public Mono<V> get(K key) {
        return mapCache.get(key);
    }

    public Mono<Set<K>> readAllKeySet() {
        return mapCache.readAllKeySet();
    }

    public Mono<Boolean> remove(K key) {
        return mapCache.fastRemove(key);
    }

    public Flux<Map.Entry<K, V>> getMultiple(Set<K> keys) {
        return mapCache.getMultiple(keys);
    }

}
