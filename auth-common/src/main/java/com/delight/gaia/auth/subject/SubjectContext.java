package com.delight.gaia.auth.subject;

import com.delight.gaia.auth.token.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectContext {
    private Token token;
    private ClientInfo clientInfo;
    private Subject subject;
}
