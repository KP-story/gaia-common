package com.delight.gaia.auth.token;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class JwtToken implements Token{
    private String token;
    private String kId;
}
