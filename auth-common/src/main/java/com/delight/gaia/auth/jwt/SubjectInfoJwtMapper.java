package com.delight.gaia.auth.jwt;

import com.delight.gaia.auth.subject.SubjectInfo;
import com.delight.gaia.base.constant.AccountType;
import com.nimbusds.jwt.JWTClaimsSet;

import java.text.ParseException;

public class SubjectInfoJwtMapper {

    public static JWTClaimsSet.Builder toClaims(SubjectInfo subjectInfo) {
        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
        builder.claim("account-type", subjectInfo.getAccountType().ordinal());
        builder.subject(subjectInfo.getId().toString());
        return builder;
    }

    public static SubjectInfo fromJwt(JWTClaimsSet claims) throws ParseException {
        SubjectInfo subjectInfo = new SubjectInfo();
        subjectInfo.setAccountType(AccountType.values()[claims.getIntegerClaim("account-type")]);
        subjectInfo.setId(Long.valueOf(claims.getSubject()));
        return subjectInfo;
    }
}
