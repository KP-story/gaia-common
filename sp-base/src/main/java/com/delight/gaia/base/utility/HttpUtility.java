package com.delight.gaia.base.utility;

import lombok.SneakyThrows;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.channels.Channels;


public class HttpUtility {

    public static String getUrl(ServerHttpRequest request) {
        return request.getURI().getPath();
    }


    public static String getIp(ServerHttpRequest request) {
        String host = getClientIp(request);
        return host;
    }

    private static final String LOCALHOST_IPV4 = "127.0.0.1";
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    public static String getClientIp(ServerHttpRequest request) {
        String ipAddress = request.getHeaders().getFirst("X-Forwarded-For");
        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeaders().getFirst("Proxy-Client-IP");
            if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeaders().getFirst("WL-Proxy-Client-IP");
                if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
                    {
                        ipAddress = request.getHeaders().getFirst("X-Real-IP");
                        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
                            ipAddress = request.getRemoteAddress().getHostString();
                            if (LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
                                try {
                                    InetAddress inetAddress = InetAddress.getLocalHost();
                                    ipAddress = inetAddress.getHostAddress();
                                } catch (UnknownHostException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!StringUtils.isEmpty(ipAddress)
                && ipAddress.length() > 15
                && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }
        return ipAddress;
    }


}
