package com.delight.gaia.auth.key;

import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Set;

public interface SecretKeyManager {
    Mono<Key> getKeyById(String id);
}
