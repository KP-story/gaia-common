package com.delight.gaia.auth.key;

import com.delight.gaia.auth.context.Realm;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.Scheduler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class DefaultKeyManager implements SecretKeyManager {
    private final Realm realm;
    private Cache<String, Key> signingKeys;

    @PostConstruct
    public void init() {
        signingKeys = Caffeine.newBuilder().scheduler(Scheduler.systemScheduler() ).expireAfterWrite(30, TimeUnit.DAYS).build();

    }

    @Override
    public Mono<Key> getKeyById(String id) {
        Key key = signingKeys.getIfPresent(id);
        if (key == null) {
            synchronized (this) {
                key = signingKeys.getIfPresent(id);
                if (key == null) {
                    return realm.getSigningKeys(id).doOnSuccess(key1 -> {
                        signingKeys.put(id, key1);
                    });
                }

            }
        }

        return Mono.just(key);
    }
}
