package com.delight.gaia.base.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.SneakyThrows;

import java.io.IOException;
import java.security.PrivateKey;

public class JsonRSAPrivateKeyDeserializer extends JsonDeserializer<PrivateKey> {


    @SneakyThrows
    @Override
    public PrivateKey deserialize(JsonParser p, DeserializationContext ctxt) {
        String valueAsString = p.getValueAsString();
        return SecurityConverter.stringToRsaPrivateKey(valueAsString);
    }
}
