package com.delight.gaia.websocket.codec;

import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageCodec;
import com.delight.gaia.base.message.GaiaMessageUtils;
import com.delight.gaia.base.vo.proto.GaiaMessageProto;
import com.delight.gaia.base.vo.proto.Metadata;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.Message;
import io.netty.handler.codec.CodecException;
import org.springframework.core.codec.DecodingException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.web.reactive.socket.WebSocketMessage;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentMap;

public class ProtoWebsocketCodec extends GaiaMessageCodec<WebSocketMessage, ByteString> {
    private static final ConcurrentMap<Class<?>, Method> methodCache = new ConcurrentReferenceHashMap<>();

    private static Message.Builder getMessageBuilder(Class<?> clazz) throws Exception {
        Method method = methodCache.get(clazz);
        if (method == null) {
            method = clazz.getMethod("newBuilder");
            methodCache.put(clazz, method);
        }
        return (Message.Builder) method.invoke(clazz);
    }

    @Override
    public GaiaMessage decode(WebSocketMessage input) throws io.netty.handler.codec.CodecException {
        GaiaMessage gaiaMessage = new GaiaMessage();
        GaiaMessageUtils.setMessageCodec(gaiaMessage, this);
        GaiaMessageProto gaiaMessageProto = decodeMessageWrapper(input.getPayload());
        GaiaMessageUtils.setRawBody(gaiaMessage, gaiaMessageProto.getPayload());
        Metadata metadata = gaiaMessageProto.getMetadata();
        gaiaMessage.setCommand(metadata.getCommand());
        if(metadata.hasStatusResponse())
        gaiaMessage.setStatusResponse(metadata.getStatusResponse());
        if(metadata.hasErrorResponse())
        gaiaMessage.setErrorResponse(metadata.getErrorResponse());
        gaiaMessage.setId(metadata.getId());
        if(!metadata.getAttributesMap().isEmpty())
            gaiaMessage.setAttribute(metadata.getAttributesMap());
        return gaiaMessage;
    }

    @Override
    public WebSocketMessage encode(GaiaMessage message) throws io.netty.handler.codec.CodecException {
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
            GaiaMessageProto gaiaMessageProto = GaiaMessageProto.newBuilder().setMetadata(metadata).setPayload(GaiaMessageUtils.getRawBody(message)).build();
            return new WebSocketMessage(WebSocketMessage.Type.BINARY, DefaultDataBufferFactory.sharedInstance.wrap(gaiaMessageProto.toByteString().asReadOnlyByteBuffer()));
        } catch (IOException ex) {
            throw new DecodingException("I/O error while parsing input stream", ex);
        } catch (Exception ex) {
            throw new DecodingException("Could not read Protobuf message: " + ex.getMessage(), ex);
        }
    }

    @Override
    public ByteString encodeBody(Object data) throws io.netty.handler.codec.CodecException {
        if (data == null) {
            return ByteString.EMPTY;
        }
        if (data instanceof Message)
            return ((Message) data).toByteString();
        else throw new ClassCastException(data.getClass().getName() + " cannot cast to Message");

    }

    @Override
    public <T> T decodeBody(Class<T> targetType, ByteString rawData) throws io.netty.handler.codec.CodecException {
        return decode(targetType, rawData);
    }

    public GaiaMessageProto decodeMessageWrapper(DataBuffer rawData) throws io.netty.handler.codec.CodecException {
        try {
            GaiaMessageProto.Builder builder = GaiaMessageProto.newBuilder();
            ByteBuffer byteBuffer = ByteBuffer.allocate(rawData.readableByteCount());
            rawData.toByteBuffer(byteBuffer);
            builder.mergeFrom(CodedInputStream.newInstance(byteBuffer));
            return builder.build();
        } catch (
                IOException ex) {
            throw new DecodingException("I/O error while parsing input stream", ex);
        } catch (
                Exception ex) {
            throw new DecodingException("Could not read Protobuf message: " + ex.getMessage(), ex);
        } finally {
            DataBufferUtils.release(rawData);
        }

    }

    public <T> T decode(Class<T> targetType, ByteString rawData) throws CodecException {
        try {
            Message.Builder builder = getMessageBuilder(targetType);
            builder.mergeFrom(rawData);
            return (T) builder.build();
        } catch (
                IOException ex) {
            throw new DecodingException("I/O error while parsing input stream", ex);
        } catch (
                Exception ex) {
            throw new DecodingException("Could not read Protobuf message: " + ex.getMessage(), ex);
        }

    }


}
