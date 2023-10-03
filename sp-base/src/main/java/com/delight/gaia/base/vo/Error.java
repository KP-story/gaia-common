package com.delight.gaia.base.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Error {
    private String message;
    private Integer code;
    private String scope;
}
