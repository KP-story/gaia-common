package com.delight.gaia.web.log;


import com.delight.gaia.base.vo.HttpRequestInfo;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class LogHttpRequestBuilder {
    public static HttpRequestInfo build(ServerRequest request) {
        HttpRequestInfo httpRequestInfo = new HttpRequestInfo();
        httpRequestInfo.setMethod(request.methodName());
        httpRequestInfo.setUrl(request.uri().toString());
        httpRequestInfo.setHeaders(headers(request));
        return httpRequestInfo;
    }


    public static Map<String, String> headers(ServerRequest request) {
        Map<String, String> headers = new HashMap<>();
        ServerRequest.Headers requestHeaders = request.headers();
        for(var header:requestHeaders.asHttpHeaders().keySet())
        {
             headers.put(header, request.headers().firstHeader(header));
        }
        return headers;
    }

    public static HttpRequestInfo build(ServerHttpRequest request) {
        HttpRequestInfo httpRequestInfo = new HttpRequestInfo();
        httpRequestInfo.setMethod(request.getMethodValue());
        httpRequestInfo.setUrl(request.getURI().toString());
        httpRequestInfo.setHeaders(headers(request));
        return httpRequestInfo;
    }


    public static Map<String, String> headers(ServerHttpRequest httpServletRequest) {
        Map<String, String> headers = new HashMap<>();
         for(var header:httpServletRequest.getHeaders().keySet())
        {
            headers.put(header, httpServletRequest.getHeaders().getFirst(header));
        }
         return headers;
    }
}
