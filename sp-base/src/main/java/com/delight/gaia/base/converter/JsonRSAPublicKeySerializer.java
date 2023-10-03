package com.delight.gaia.base.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;

import java.io.IOException;
import java.security.PublicKey;

public class JsonRSAPublicKeySerializer extends JsonSerializer<PublicKey> {

    @SneakyThrows
    @Override
    public void serialize(PublicKey value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(SecurityConverter.rsaPublicKeyToString(value));
    }
}
