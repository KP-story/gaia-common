package com.delight.gaia.base.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.SneakyThrows;

import java.io.IOException;
import java.security.PublicKey;

public class JsonRSAPublicKeyDeserializer extends JsonDeserializer<PublicKey> {


    @SneakyThrows
    @Override
    public PublicKey deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String valueAsString = p.getValueAsString();
        return SecurityConverter.stringToRsaPublicKey(valueAsString);
    }
}
