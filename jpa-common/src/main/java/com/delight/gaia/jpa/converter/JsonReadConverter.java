package com.delight.gaia.jpa.converter;

import com.delight.gaia.jpa.converter.annotation.JsonRawConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class JsonReadConverter<T, V> implements Converter<V, T> {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JsonRawConverter<V> jsonReadConverter;
    private JavaType typeReference;
    @PostConstruct
    public void init() {
        typeReference= objectMapper.getTypeFactory().constructType( ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);

    }

    @SneakyThrows
    @Override
    public T convert(V source) {
        return objectMapper.readValue(jsonReadConverter.toByte(source), typeReference);
    }
}