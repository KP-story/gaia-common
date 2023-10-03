package com.delight.gaia.jpa.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JpaConfig {
    private String url;
    private String user;
    private String password;
    private Integer minimumIdle = 1;
    private Integer maximumPoolSize = 5;
    private String dialect;
}
