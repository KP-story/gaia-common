package com.delight.gaia.base.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@Slf4j
public class GaiaMessage {
    private Map<String, String> attributes;
    private Object rawBody;
    private String command;
    private Long id;
    private Object body;
    private Integer statusResponse;
    private String errorResponse;

    private GaiaMessageCodec gaiaMessageCodec;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public Integer getStatusResponse() {
        return statusResponse;
    }

    public void setStatusResponse(int statusResponse) {
        this.statusResponse = statusResponse;
    }

    public String getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    public String getAttribute(String name) {
        if (attributes == null) {
            return null;
        }
        return attributes.get(name);
    }

    public Integer getIntAttribute(String name) {
        String value = getAttribute(name);
        if (value == null) {
            return null;
        }
        return Integer.valueOf(value);
    }

    public Double getDoubleAttribute(String name) {
        String value = getAttribute(name);
        if (value == null) {
            return null;
        }
        return Double.valueOf(value);
    }

    public Long getLongAttribute(String name) {
        String value = getAttribute(name);
        if (value == null) {
            return null;
        }
        return Long.valueOf(value);
    }

    public Boolean getBoolAttribute(String name) {
        String value = getAttribute(name);
        if (value == null) {
            return null;
        }
        return Boolean.valueOf(value);
    }

    public Short getShortAttribute(String name) {
        String value = getAttribute(name);
        if (value == null) {
            return null;
        }
        return Short.valueOf(value);
    }

    public Byte getByteAttribute(String name) {
        String value = getAttribute(name);
        if (value == null) {
            return null;
        }
        return Byte.valueOf(value);
    }

    public void addAttribute(String name, String data) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        attributes.put(name, data);
    }

    public void setAttribute(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public <T> T getBody(Class<T> cz) {
        if (body == null) {
            decodeBody(cz);
        }
        return (T) body;
    }

    public void setBody(Object body) {
        this.body = body;
    }


    void reEncode() {
        if (body != null) {
            rawBody = getGaiaMessageCodec().encodeBody(body);
        }
    }

    <T> T getRawBody() {
        if (rawBody == null) {
            rawBody = getGaiaMessageCodec().encodeBody(body);
        }
        return (T) rawBody;
    }
    <T> T  _getRawBody() {
        return (T) rawBody;
    }

    private void decodeBody(Class cz) {
        if (rawBody != null) {
            body = gaiaMessageCodec.decodeBody(cz, rawBody);
        }

    }


    public void setBodyAndEncode(Object body) {
        this.body = body;
        reEncode();
    }


}
