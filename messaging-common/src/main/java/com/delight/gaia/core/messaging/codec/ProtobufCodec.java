package com.delight.gaia.core.messaging.codec;

import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageCodec;
import com.delight.gaia.base.message.GaiaMessageUtils;
import com.delight.gaia.base.vo.proto.Metadata;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.CodecException;
import io.rsocket.Payload;
import io.rsocket.util.ByteBufPayload;
import org.springframework.core.codec.DecodingException;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentMap;

public class ProtobufCodec extends GaiaMessageCodec<Payload, ByteBuf> {
    private static final ConcurrentMap<Class<?>, Method> methodCache = new ConcurrentReferenceHashMap<>();

    @Override
    public GaiaMessage decode(Payload input) throws CodecException {
        GaiaMessage gaiaMessage = new GaiaMessage();
        GaiaMessageUtils.setMessageCodec(gaiaMessage, this);
        GaiaMessageUtils.setRawBody(gaiaMessage, input.sliceData().retain());
        Metadata metadata = decodeMetadata(input.sliceMetadata().retain());
        gaiaMessage.setCommand(metadata.getCommand());
        gaiaMessage.setStatusResponse(metadata.getStatusResponse());
        gaiaMessage.setErrorResponse(metadata.getErrorResponse());
        gaiaMessage.setId(metadata.getId());
        gaiaMessage.setAttribute(metadata.getAttributesMap());
        return gaiaMessage;
    }

    @Override
    public Payload encode(GaiaMessage message) throws CodecException {
        try {
            GaiaMessageUtils.setMessageCodec(message, this);
            Metadata.Builder metadataBuilder = Metadata.newBuilder();
            if (message.getCommand() != null)
                metadataBuilder.setCommand(message.getCommand());
            if (message.getId() != null)
                metadataBuilder.setId(message.getId());
            if (message.getAttributes() != null) {
                metadataBuilder.putAllAttributes(message.getAttributes());
            }
            if (message.getStatusResponse() != null) {
                metadataBuilder.setStatusResponse(message.getStatusResponse());
            }
            if (message.getErrorResponse() != null) {
                metadataBuilder.setErrorResponse(message.getErrorResponse());
            }
            Metadata metadata = metadataBuilder.build();
            return ByteBufPayload.create(GaiaMessageUtils.getRawBody(message), writeTo(metadata));
        } catch (IOException ex) {
            throw new DecodingException("I/O error while parsing input stream", ex);
        } catch (Exception ex) {
            throw new DecodingException("Could not read Protobuf message: " + ex.getMessage(), ex);
        }
    }

    @Override
    public ByteBuf encodeBody(Object data) throws CodecException {
        if (data == null) {
            return Unpooled.EMPTY_BUFFER;
        }
        if (data instanceof Message)
            return writeTo((Message) data);
        else return Unpooled.EMPTY_BUFFER;
    }

    @Override
    public <T> T decodeBody(Class<T> targetType, ByteBuf rawData) throws CodecException {
        return decode(targetType, rawData);
    }

    public Metadata decodeMetadata(ByteBuf rawData) throws CodecException {
        try {
            Metadata.Builder builder = Metadata.newBuilder();
            builder.mergeFrom(CodedInputStream.newInstance(ByteBufUtil.getBytes(rawData, rawData.readerIndex(), rawData.readableBytes(), false)));
            return builder.build();
        } catch (
                IOException ex) {
            throw new DecodingException("I/O error while parsing input stream", ex);
        } catch (
                Exception ex) {
            throw new DecodingException("Could not read Protobuf message: " + ex.getMessage(), ex);
        } finally {
            if (rawData.refCnt() > 0) {
                rawData.release();
            }
        }

    }

    public <T> T decode(Class<T> targetType, ByteBuf rawData) throws CodecException {
        try {
            Message.Builder builder = getMessageBuilder(targetType);
            builder.mergeFrom(CodedInputStream.newInstance(ByteBufUtil.getBytes(rawData, rawData.readerIndex(), rawData.readableBytes(), false)));
            return (T) builder.build();
        } catch (
                IOException ex) {
            throw new DecodingException("I/O error while parsing input stream", ex);
        } catch (
                Exception ex) {
            throw new DecodingException("Could not read Protobuf message: " + ex.getMessage(), ex);
        }

    }

    private static Message.Builder getMessageBuilder(Class<?> clazz) throws Exception {
        Method method = methodCache.get(clazz);
        if (method == null) {
            method = clazz.getMethod("newBuilder");
            methodCache.put(clazz, method);
        }
        return (Message.Builder) method.invoke(clazz);
    }

    ByteBuf writeTo(Message message) {
        ByteBuf buffer = null;
        try {
            byte[] data = message.toByteArray();
            buffer = PooledByteBufAllocator.DEFAULT.directBuffer(data.length);
            buffer.writeBytes(data);
            return buffer;
        } catch (Exception e) {
            if (buffer != null) {
                buffer.release();
            }
            throw e;
        }

    }


}
