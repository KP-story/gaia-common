package com.delight.gaia.auth.jwt;

import com.delight.gaia.base.exception.AuthenticationException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory;
import com.nimbusds.jose.proc.JWSVerifierFactory;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.text.ParseException;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenVerifier {
    private JWSVerifierFactory jwsVerifierFactory = new DefaultJWSVerifierFactory();

    public SignedJWT parse(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        return signedJWT;
    }

    public void verify(SignedJWT signedJWT, Key key) throws Exception {
        JWSVerifier jwsVerifier = jwsVerifierFactory.createJWSVerifier(signedJWT.getHeader(), key);
        if (!signedJWT.verify(jwsVerifier)) {
            throw new AuthenticationException("token invalid");
        }
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (expirationTime != null && expirationTime.before(new Date())) {
            throw new AuthenticationException("token expired");
        }
    }


}
