package com.delight.gaia.auth.subject;

import com.delight.gaia.base.vo.UserDisplayInfo;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface Subject {
    Long getId();

    ClientInfo getClientInfo();

    SubjectInfo getInfo();

    Mono<UserDisplayInfo> getDisplayInfo();

    Map<Object, Object> getExtraInfo();

    Subject setClientInfo(ClientInfo clientInfo);

    Mono<Boolean> isPermitted(String permission);

    Mono<Boolean> checkPermission(String permission);


}
