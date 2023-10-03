package com.delight.gaia.connector.http.config;

import com.delight.gaia.connector.http.SpringFeignHttpClientFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class HttpConfig {
    private boolean logging = true;
    private ConnectionConfig connection = new ConnectionConfig();
    private String baseUrl;

    private String factory =SpringFeignHttpClientFactory.class.getName();
    private String name;


}
