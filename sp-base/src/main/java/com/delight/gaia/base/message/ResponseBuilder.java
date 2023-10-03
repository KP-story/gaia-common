package com.delight.gaia.base.message;

import com.delight.gaia.base.lang.TranslateService;
import com.delight.gaia.base.vo.Error;
import com.delight.gaia.base.vo.Message;
import com.delight.gaia.base.vo.ResultCode;

import java.io.IOException;
import java.util.Locale;

public class ResponseBuilder {
    protected static TranslateService translateService;

    public void setTranslateService(TranslateService translateService) {
        ResponseBuilder.translateService = translateService;
    }

    public static HttpErrorResponse error(Locale locale, ResultCode resultCode) {
        Message message = new Message();
        String desc = translateService.translate(locale, resultCode.getMessage());
        message.setCode(resultCode.getCode()).setMessage(desc);
        return new HttpErrorResponse(message, resultCode.getHttpCode());
    }

    public static HttpErrorResponse error(Locale locale, ResultCode resultCode, String... args) {
        Message message = new Message();
        String desc = translateService.translateWithArgs(locale, resultCode.getMessage(), args);
        message.setCode(resultCode.getCode()).setMessage(desc);
        return new HttpErrorResponse(message, resultCode.getHttpCode());
    }

    public static HttpErrorResponse error(Locale locale, ResultCode resultCode, Error... errors) {
        Message message = new Message();
        String desc = translateService.translate(locale, resultCode.getMessage());
        message.setCode(resultCode.getCode()).setMessage(desc).setErrors(errors);
        return new HttpErrorResponse(message, resultCode.getHttpCode());
    }


    public static GaiaMessageResponse errorGaiaMsg(Locale locale, GaiaMessage request, ResultCode resultCode) throws IOException {
        String desc = translateService.translate(locale, resultCode.getMessage());
        GaiaMessageResponse gaiaMessage = new GaiaMessageResponse(request, resultCode.getCode(), desc);
        return gaiaMessage;
    }

    public static GaiaMessageResponse errorGaiaMsg(Locale locale, GaiaMessage request, ResultCode resultCode, String... args) throws IOException {
        String desc = translateService.translateWithArgs(locale, resultCode.getMessage(), args);
        GaiaMessageResponse gaiaMessage = new GaiaMessageResponse(request, resultCode.getCode(), desc);
        return gaiaMessage;
    }

    public static GaiaMessageResponse errorGaiaMsg(Locale locale, GaiaMessage request, ResultCode resultCode, Error... errors) throws IOException {
        String desc = translateService.translate(locale, resultCode.getMessage());
        GaiaMessageResponse gaiaMessage = new GaiaMessageResponse(request, resultCode.getCode(),desc);
        return gaiaMessage;

    }

}
