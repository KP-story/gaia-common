package com.delight.gaia.base.converter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SecurityConverter {
    public static PrivateKey stringToRsaPrivateKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key));
        PrivateKey privateKey = kf.generatePrivate(keySpecPKCS8);
        return privateKey;
    }

    public static PublicKey stringToRsaPublicKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
        return kf.generatePublic(keySpecX509);
    }

    public static String rsaPrivateKeyToString(PrivateKey key) throws NoSuchAlgorithmException {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(key.getEncoded());
    }

    public static String rsaPublicKeyToString(PublicKey key) throws NoSuchAlgorithmException {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(key.getEncoded());
    }


    public static PublicKey decodeRsaPublicKey(String n, String e) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        Base64.Decoder urlDecoder = Base64.getUrlDecoder();
        byte[] modulus = urlDecoder.decode(n);
        byte[] exponent = urlDecoder.decode(e);
        RSAPublicKeySpec spec = new RSAPublicKeySpec(new BigInteger(1,modulus), new BigInteger(1,exponent));
        return kf.generatePublic(spec);
    }

    public static RSAHolder encodeRSAPublicKey(RSAPublicKey key) throws NoSuchAlgorithmException {
        Base64.Encoder encoder = Base64.getUrlEncoder();
        String n = encoder.encodeToString(BigIntegerUtils.toBytesUnsigned(key.getModulus()));
        String e = encoder.encodeToString(BigIntegerUtils.toBytesUnsigned(key.getPublicExponent()));
        return new RSAHolder().setE(e).setN(n);
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class RSAHolder {
        private String n;
        private String e;

    }
}
