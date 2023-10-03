package com.delight.gaia.base.message;

import org.springframework.core.codec.CodecException;

public abstract class GaiaMessageCodec<T, B> {

    public abstract GaiaMessage decode(T input) throws CodecException;

    public abstract T encode(GaiaMessage message) throws CodecException;

    public abstract B encodeBody(Object data) throws CodecException;

    public abstract <T> T decodeBody(Class<T> data, B rawData) throws CodecException;


}
