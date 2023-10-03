package com.delight.gaia.auth.context;

import com.delight.gaia.auth.subject.Subject;
import com.delight.gaia.auth.subject.SubjectContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ReactiveSubjectHolder implements SubjectHolder {
    private static final Class<?> SECURITY_SUB_CONTEXT_KEY = SubjectContext.class;
   private   final GaiaSecurityManager gaiaSecurityManager;

    @Override
    public Mono<Subject> getSubject() {
        return Mono.deferContextual(ctx -> ctx.<Mono<SubjectContext>>get(SECURITY_SUB_CONTEXT_KEY))
                .flatMap(subjectContext -> subjectContext.getSubject()==null?gaiaSecurityManager.verify(subjectContext).map(subject -> {
                    subjectContext.setSubject(subject);
                    subjectContext.setToken(null);
                    return subject;
                }):Mono.just(subjectContext.getSubject()));
    }


    @Override
    public Function<Context, Context> setSubjectContext(Mono<SubjectContext> subject) {
        return context -> context.put(SECURITY_SUB_CONTEXT_KEY, subject);
    }


    @Override
    public Function<Context, Context> clearSubject() {
        return context -> context.delete(SECURITY_SUB_CONTEXT_KEY);
    }


}
