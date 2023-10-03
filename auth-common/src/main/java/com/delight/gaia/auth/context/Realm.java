package com.delight.gaia.auth.context;

import com.delight.gaia.auth.subject.ClientInfo;
import com.delight.gaia.auth.subject.Subject;
import com.delight.gaia.auth.subject.SubjectInfo;
import com.delight.gaia.base.constant.AccountType;
import com.delight.gaia.base.vo.UserDisplayInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Realm {


    Flux<PermissionStatus> checkPermission(Long userId, String... permissions);

    Flux<PermissionStatus> checkPermission(Long userId, AccountType accountType, String... permissions);

    Mono<Key> getSigningKeys(String kId);

    Mono< UserDisplayInfo> getDisplayInfo(Long userId);

    Mono<Subject> makeSubject(SubjectInfo requesterInfo, ClientInfo clientInfo);

    Mono<Boolean> allowDomain(String domain);

    List<String> allowHeaders(String domain);

    Flux<Map.Entry<Long,UserDisplayInfo>> getDisplayInfos(Set<Long> userIds);
}
