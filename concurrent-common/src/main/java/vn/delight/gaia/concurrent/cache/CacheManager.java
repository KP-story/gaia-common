package vn.delight.gaia.concurrent.cache;

public interface CacheManager {
    String getId();


    KMapCache _getCache(String name, CacheConfig cacheConfig);

    default KMapCache getCache(String name) {
        return _getCache("id." + name,null);
    }

    default KMapCache getCache(String name, CacheConfig cacheConfig) {
        return _getCache("id." + name, cacheConfig);
    }

}
