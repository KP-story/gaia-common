package com.delight.gaia.auth.filter;

import com.delight.gaia.auth.context.GaiaSecurityManager;
import com.delight.gaia.auth.context.SubjectHolder;
import com.delight.gaia.auth.subject.ClientInfo;
import com.delight.gaia.auth.subject.Subject;
import com.delight.gaia.auth.subject.SubjectContext;
import com.delight.gaia.auth.token.Token;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public abstract class DefaultAuthFilter<R, RS> implements AuthFilter<R, RS> {
    private SubjectHolder subjectHolder;
    private GaiaSecurityManager securityManager;

    @Override
    public Mono<SubjectContext> doProcessAuth(R r, RS rs) {
        Token token = detectToken(r);
        Mono<SubjectContext> subjectContext = securityManager.detect(token,new ClientInfo().setApp(detectApp(r))).map(subject -> {
            updateClientInfo(r, subject.getClientInfo());
            return subject;
        });
        return subjectContext;
    }


    @Override
    public Mono<SubjectContext> afterProcess(Mono mono, Mono<SubjectContext> subject) {
        return mono.contextWrite(subjectHolder.setSubjectContext(subject));
    }

}
