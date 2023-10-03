package com.delight.gaia.jpa.converter.annotation;

public interface JsonRawConverter<T> {
    byte[] toByte(T toByte);

    T fromByte(byte[] bytes);
}
