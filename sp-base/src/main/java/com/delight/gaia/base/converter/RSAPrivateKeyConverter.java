package com.delight.gaia.base.converter;

import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;


@ConfigurationPropertiesBinding
@Component
public class RSAPrivateKeyConverter implements Converter<String, PrivateKey> {

    @SneakyThrows
    @Override
    public PrivateKey convert(String value) {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(value));
        PrivateKey privateKey = kf.generatePrivate(keySpecPKCS8);
        return privateKey;
    }


}
