package com.delight.gaia.connector.http;

import org.springframework.beans.factory.DisposableBean;

import java.util.Collection;

public interface HttpClientFactory extends DisposableBean {
    <T> T createService(Class<T> clazz);

    void init();

    Collection<Object> getAllService();


}
