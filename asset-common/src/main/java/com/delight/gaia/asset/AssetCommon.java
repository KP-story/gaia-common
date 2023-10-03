package com.delight.gaia.asset;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface AssetCommon {
    Mono<String> getPrefixUrl(String config);

    Mono<String> getAccountAvatar();

    String getAccountAvatarSync();

    Mono<String> getChatChannelAvatar();
    String getChatChannelAvatarSync();

    Mono<Map<String, String>> getPrefixUrls(List<String> config);
}
