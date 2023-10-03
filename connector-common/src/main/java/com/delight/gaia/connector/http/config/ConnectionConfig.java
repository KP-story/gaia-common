package com.delight.gaia.connector.http.config;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ConnectionConfig {
    private int timeout = 5;
    private int maxConnection = 10;
    private int idleTime = 60;
}