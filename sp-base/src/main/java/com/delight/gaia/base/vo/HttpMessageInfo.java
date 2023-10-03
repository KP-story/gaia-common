package com.delight.gaia.base.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HttpMessageInfo {
    private String body;
    private Map<String, String> headers;
    private String requestId;
    private String url;
    private String method;
}
