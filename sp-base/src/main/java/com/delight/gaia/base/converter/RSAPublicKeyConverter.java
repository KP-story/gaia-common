package com.delight.gaia.base.converter;

import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@ConfigurationPropertiesBinding
@Component
public class RSAPublicKeyConverter implements Converter<String, PublicKey> {
    @SneakyThrows
    @Override
    public PublicKey convert(String source) {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(source));
        return kf.generatePublic(keySpecX509);
    }
}
