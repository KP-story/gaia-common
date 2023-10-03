package com.delight.gaia.base.message;

public class GaiaMessageResponse extends GaiaMessage {
    public GaiaMessageResponse(Integer code, String response) {
        setStatusResponse(code);
        setErrorResponse(response);
    }

    public GaiaMessageResponse(GaiaMessage gaiaMessage, Integer code, String message) {
        this(code, message);
        setCommand(gaiaMessage.getCommand());
        setId(gaiaMessage.getId());
    }
}
