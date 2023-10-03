package com.delight.gaia.base.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class HttpResponseInfo extends HttpMessageInfo {
    private int status;

}
