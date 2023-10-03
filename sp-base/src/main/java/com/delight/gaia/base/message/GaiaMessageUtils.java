package com.delight.gaia.base.message;

import java.io.IOException;
import java.util.Map;

public class GaiaMessageUtils {
    public static void setAttribute(GaiaMessage gaiaMessage, Map<String, String> attributes) {
        gaiaMessage.setAttributes(attributes);
    }

    public static Map<String, String> getAttribute(GaiaMessage gaiaMessage) {
        return gaiaMessage.getAttributes();
    }

    public static <B> void setRawBody(GaiaMessage gaiaMessage, B rawBody) {
        gaiaMessage.setRawBody(rawBody);
    }

    public static void setMessageCodec(GaiaMessage gaiaMessage, GaiaMessageCodec gaiaMessageCodec) {
        gaiaMessage.setGaiaMessageCodec(gaiaMessageCodec);
    }

    public static void reEncode(GaiaMessage gaiaMessage) throws IOException {
        gaiaMessage.reEncode();
    }

    public static <B> B getRawBody(GaiaMessage gaiaMessage) throws IOException {
        return gaiaMessage.getRawBody();
    }
    public static <B> B _getRawBody(GaiaMessage gaiaMessage) throws IOException {
        return gaiaMessage._getRawBody();
    }

}
