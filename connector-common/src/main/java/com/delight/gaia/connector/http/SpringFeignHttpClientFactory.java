package com.delight.gaia.connector.http;

import com.delight.gaia.connector.http.config.HttpConfig;
import com.delight.gaia.connector.http.codec.GaiaEncoder;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import org.springframework.http.HttpHeaders;

public class SpringFeignHttpClientFactory extends FeignHttpClientFactory {

    public SpringFeignHttpClientFactory(HttpConfig httpConfig) {
        super(httpConfig);
    }

    @Override
    public Feign.Builder config(Feign.Builder builder) {
        builder.decoder(new JacksonDecoder()).encoder(new GaiaEncoder()).build();
        builder.requestInterceptor(template -> {
            if (!template.headers().containsKey(HttpHeaders.CONTENT_TYPE)) {
                template.header(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
            }
        });
        return super.config(builder);
    }
}
