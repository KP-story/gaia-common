package com.delight.gaia.web;

import com.delight.gaia.base.lang.TranslateService;
import com.delight.gaia.base.message.ResponseBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Configuration
public class BaseAutoConfig {
    @Bean
    @ExceptionHandler
    protected ResponseBuilder responseBuilder(TranslateService translateService) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.setTranslateService(translateService);
        return responseBuilder;
    }

}
