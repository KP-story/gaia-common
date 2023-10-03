package vn.delight.gaia.concurrent.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import lombok.RequiredArgsConstructor;
import vn.delight.gaia.concurrent.cache.CacheConfig;
import vn.delight.gaia.concurrent.cache.CacheManager;
import vn.delight.gaia.concurrent.cache.KMapCache;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class CaffeineCacheManager implements CacheManager {
    private final String id;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public KMapCache _getCache(String name, CacheConfig cacheConfig) {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder();
        Cache cache = null;
        if (cacheConfig != null) {
            if (cacheConfig.getMaxSize() != null) {
                caffeine.maximumSize(cacheConfig.getMaxSize());
            }
            if (cacheConfig.getIdleTime() != null) {
                caffeine.expireAfterAccess(cacheConfig.getIdleTime(), TimeUnit.SECONDS);
            }
            if (cacheConfig.getTtl() != null) {
                caffeine.expireAfterWrite(cacheConfig.getTtl(), TimeUnit.SECONDS);
            }

        }
        cache = caffeine.scheduler(Scheduler.systemScheduler()).build();
        return new CaffeineMapCache(cache, cacheConfig.getLoader(),cacheConfig.getMultipleLoader());
    }
}
