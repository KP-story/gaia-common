package com.delight.gaia.base.vo;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultCode {
    private Integer code;
    private String message;
    private HttpStatus httpCode;
}
