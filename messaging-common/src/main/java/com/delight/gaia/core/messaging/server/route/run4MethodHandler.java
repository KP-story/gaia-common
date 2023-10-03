package com.delight.gaia.core.messaging.server.route;

import com.delight.gaia.base.message.GaiaMessage;
import reactor.core.publisher.Mono;


import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.core.messaging.server.route.IMethodHandler;
import reactor.core.publisher.Mono;

public class run4MethodHandler extends IMethodHandler {
    public Object[] getParameters(GaiaMessage var1) {
        Object[] var2 = new Object[]{var1, var1.getBody(String.class), var1.getAttributes(), var1.getAttribute("demo")};
        return var2;
    }

    public Mono handle(GaiaMessage var1) throws Exception {
        return this._handle(var1);
    }

    public run4MethodHandler() {
    }

    public static void main(String[] args) throws Exception {
        run4MethodHandler.class.getConstructor().newInstance();
    }
}