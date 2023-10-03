package com.delight.gaia.connector.http.codec;

import feign.RequestTemplate;
import feign.form.spring.SpringFormEncoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.openfeign.encoding.HttpEncoding;
import org.springframework.http.MediaType;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

public class GaiaEncoder extends JacksonEncoder {
    private SpringFormEncoder springFormEncoder= new SpringFormEncoder();

    @Override
    public void encode(Object requestBody, Type bodyType, RequestTemplate request) {
        if (request != null) {
            Collection<String> contentTypes = request.headers().get(HttpEncoding.CONTENT_TYPE);

            MediaType requestContentType = null;
            if (contentTypes != null && !contentTypes.isEmpty()) {
                String type = contentTypes.iterator().next();
                requestContentType = MediaType.valueOf(type);
            }

            if (isMultipartType(requestContentType)) {
                this.springFormEncoder.encode(requestBody, bodyType, request);
                return;
            }
            super.encode(requestBody, bodyType, request);
        }


    }

    private boolean isMultipartType(MediaType requestContentType){
        return Arrays.asList(MediaType.MULTIPART_FORM_DATA, MediaType.MULTIPART_MIXED, MediaType.MULTIPART_RELATED)
                .contains(requestContentType);
    }
}