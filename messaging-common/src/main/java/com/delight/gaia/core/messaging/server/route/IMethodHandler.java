package com.delight.gaia.core.messaging.server.route;

import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;

public abstract class IMethodHandler {
    private Method method;
    private Object object;

    public void init(Method method, Object object) {
        this.method = method;
        this.object = object;
    }

    public abstract Object[] getParameters(GaiaMessage gaiaMessage);

    public abstract Mono handle(GaiaMessage gaiaMessage) throws Exception;

    public  Mono _handle(GaiaMessage gaiaMessage) throws Exception {
        Object[] param = getParameters(gaiaMessage);
        return param == null ? (Mono) method.invoke(object) : (Mono) method.invoke(object, param);
    }

    public  Mono _handleWrap(GaiaMessage gaiaMessage) throws Exception {
        Object[] param = getParameters(gaiaMessage);
        Mono<Object> data = param == null ? (Mono) method.invoke(object) : (Mono) method.invoke(object, param);
        return data.map(o -> {
            GaiaMessageResponse gaiaMessageResponse;
            gaiaMessageResponse = new GaiaMessageResponse(gaiaMessage, MessageCode.SUCCESS.getCode(), null);
            gaiaMessageResponse.setBody(o);
            return gaiaMessageResponse;
        });
    }
}
