package com.delight.gaia.base.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoteAddr {
    private String host;
    private int port;

    public RemoteAddr() {
    }

    public RemoteAddr(String host, int port) {
        this.host = host;
        this.port = port;
    }
}