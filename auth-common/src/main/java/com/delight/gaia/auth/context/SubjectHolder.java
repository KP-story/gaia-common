package com.delight.gaia.auth.context;

import com.delight.gaia.auth.subject.Subject;
import com.delight.gaia.auth.subject.SubjectContext;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.function.Function;

public interface SubjectHolder {
    Mono<Subject> getSubject();

    Function<Context, Context> setSubjectContext(Mono<SubjectContext> subject);

    Function<Context, Context> clearSubject();
}
