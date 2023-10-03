package com.delight.gaia.base.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;

import java.io.IOException;
import java.security.PrivateKey;

public class JsonRSAPrivateKeySerializer extends JsonSerializer<PrivateKey> {


    @SneakyThrows
    @Override
    public void serialize(PrivateKey value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(SecurityConverter.rsaPrivateKeyToString(value));
    }
}
