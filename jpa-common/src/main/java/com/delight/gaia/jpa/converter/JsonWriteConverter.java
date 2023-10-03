package com.delight.gaia.jpa.converter;

import com.delight.gaia.jpa.converter.annotation.JsonRawConverter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
public class JsonWriteConverter<T, V> implements Converter<T, V> {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JsonRawConverter<V> jsonReadConverter;


    @SneakyThrows
    @Override
    public V convert(T source) {
        return jsonReadConverter.fromByte(objectMapper.writeValueAsBytes(source));
    }
}