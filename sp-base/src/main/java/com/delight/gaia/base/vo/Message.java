package com.delight.gaia.base.vo;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Integer code;
    private String message;
    private Error[] errors;
}
