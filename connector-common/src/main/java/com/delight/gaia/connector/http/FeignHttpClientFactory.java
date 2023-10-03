package com.delight.gaia.connector.http;

import com.delight.gaia.connector.http.config.HttpConfig;
import feign.Feign;
import feign.Request;
import feign.reactive.ReactorFeign;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import java.util.concurrent.TimeUnit;

public class FeignHttpClientFactory extends BaseHttpClientFactory implements HttpClientFactory  {
    protected OkHttpClient okHttpClient;
    protected Feign.Builder builder;

    public FeignHttpClientFactory(HttpConfig httpConfig) {
        super(httpConfig);
    }

    @Override
    public <T> T _createService(Class<T> clazz) {
        return builder.target(clazz, httpConfig.getBaseUrl());
    }

    @Override
    public void _init() {
        ConnectionPool connectionPool = new ConnectionPool(httpConfig.getConnection().getMaxConnection(), httpConfig.getConnection().getIdleTime(), TimeUnit.SECONDS);
         okHttpClient = new OkHttpClient.Builder()
                .connectionPool(connectionPool)
                .build();
      var option=  new Request.Options(httpConfig.getConnection().getTimeout(),TimeUnit.SECONDS,httpConfig.getConnection().getTimeout(),TimeUnit.SECONDS,true);
        builder = ReactorFeign.builder().options(option).contract(new SpringMvcContract()).client(new feign.okhttp.OkHttpClient(okHttpClient));
        config(builder);
    }

    protected Feign.Builder config(Feign.Builder builder) {
        return builder;
    }

    @Override
    public void destroy() {
        okHttpClient.connectionPool().evictAll();
    }


}
