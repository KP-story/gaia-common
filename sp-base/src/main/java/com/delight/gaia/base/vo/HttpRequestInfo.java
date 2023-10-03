package com.delight.gaia.base.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HttpRequestInfo extends HttpMessageInfo {
    private Map<String, String> requestParameters;
    private String srcIp;
}
