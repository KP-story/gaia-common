package com.delight.gaia.auth.context;

import com.delight.gaia.auth.jwt.JwtTokenVerifier;
import com.delight.gaia.auth.jwt.SubjectInfoJwtMapper;
import com.delight.gaia.auth.subject.ClientInfo;
import com.delight.gaia.auth.subject.Subject;
import com.delight.gaia.auth.subject.SubjectContext;
import com.delight.gaia.auth.token.JwtToken;
import com.delight.gaia.auth.token.Token;
import com.delight.gaia.base.exception.AuthenticationException;
import com.delight.gaia.base.vo.UserDisplayInfo;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
@Slf4j
public class GaiaSecurityManager {
    private Realm realm;
    private JwtTokenVerifier jwtTokenVerifier;

    public Mono<SubjectContext> detect(Token token, ClientInfo clientInfo) {
        SubjectContext subjectContext = new SubjectContext();
        subjectContext.setToken(token);
        subjectContext.setClientInfo(clientInfo);
        return Mono.just(subjectContext);
    }

    public Mono<Subject> verify(Token token, ClientInfo clientInfo) {
        try {
            if (token == null) {
                return realm.makeSubject(null, clientInfo);
            }
            JwtToken jwtToken = (JwtToken) token;
            SignedJWT signedJWT = jwtTokenVerifier.parse(jwtToken.getToken());
            return realm.getSigningKeys(signedJWT.getHeader().getKeyID()).flatMap(key -> {
                try {
                    jwtTokenVerifier.verify(signedJWT, key);
                    var info = SubjectInfoJwtMapper.fromJwt(signedJWT.getJWTClaimsSet());
                    String app = signedJWT.getJWTClaimsSet().getAudience().get(0);
                    return realm.makeSubject(info, clientInfo.setApp(app));
                } catch (Exception e) {
                    return Mono.error(new AuthenticationException("verify failed", e));
                }
            });
        } catch (Exception e) {
            log.error("verify token failed ", e);
            return Mono.error(new AuthenticationException());

        }


    }


    public Mono< UserDisplayInfo> getDisplayInfo(Long userId) {
        return realm.getDisplayInfo(userId);
    }

    public Flux<Map.Entry<Long, UserDisplayInfo>> getDisplayInfos(Set<Long> userIds) {
        return realm.getDisplayInfos(userIds);
    }

    public Mono<Subject> verify(SubjectContext subjectContext) {
        return verify(subjectContext.getToken(), subjectContext.getClientInfo());

    }
}
