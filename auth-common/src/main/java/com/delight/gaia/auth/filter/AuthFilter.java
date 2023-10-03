package com.delight.gaia.auth.filter;

import com.delight.gaia.auth.subject.ClientInfo;
import com.delight.gaia.auth.subject.Subject;
import com.delight.gaia.auth.subject.SubjectContext;
import com.delight.gaia.auth.token.Token;
import reactor.core.publisher.Mono;

public interface AuthFilter<Request, Response> {
    Mono<SubjectContext> doProcessAuth(Request request, Response response);

    Mono<SubjectContext> afterProcess(Mono mono, Mono<SubjectContext> subject);

    Token detectToken(Request request);

    String detectApp(Request request);

    void updateClientInfo(Request request, ClientInfo clientInfo);
}
