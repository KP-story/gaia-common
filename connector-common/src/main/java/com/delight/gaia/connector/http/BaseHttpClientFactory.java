package com.delight.gaia.connector.http;

import com.delight.gaia.connector.http.config.HttpConfig;
import jakarta.annotation.PostConstruct;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseHttpClientFactory implements HttpClientFactory {
    private Map<Class, Object> services = new ConcurrentHashMap<>();
    protected HttpConfig httpConfig;

    public BaseHttpClientFactory(HttpConfig httpConfig) {
        this.httpConfig = httpConfig;
    }

    @Override
    public Collection<Object> getAllService() {
        return services.values();
    }

    @Override
    public <T> T createService(Class<T> clazz) {
        return (T) services.computeIfAbsent(clazz, aClass -> {
            return _createService(aClass);
        });
    }

    protected abstract <T> T _createService(Class<T> clazz);


    @Override
    @PostConstruct
    public synchronized void init() {
        _init();
    }

    public abstract void _init();
}
