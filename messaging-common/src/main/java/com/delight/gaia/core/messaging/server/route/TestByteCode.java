package com.delight.gaia.core.messaging.server.route;

import com.delight.gaia.base.message.GaiaMessage;

public class TestByteCode {
    private String a;
    private String b;

    public Object[] method(GaiaMessage gaiaMessage) {
        Object[] objects = new Object[4];
        objects[0] = gaiaMessage;
        objects[1] = gaiaMessage.getBody(String.class);
        objects[2] = gaiaMessage.getAttributes();
        String a = gaiaMessage.getAttribute("khanhlv");
        objects[3] = a == null ? null : Integer.valueOf(a);
        return objects;
    }
}
