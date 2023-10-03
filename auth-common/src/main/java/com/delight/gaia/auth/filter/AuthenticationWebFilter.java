package com.delight.gaia.auth.filter;

import com.delight.gaia.auth.context.GaiaSecurityManager;
import com.delight.gaia.auth.context.SubjectHolder;
import com.delight.gaia.auth.subject.ClientInfo;
import com.delight.gaia.auth.token.JwtToken;
import com.delight.gaia.auth.token.Token;
import com.delight.gaia.base.constant.CommonHeaders;
import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.constant.Platform;
import com.delight.gaia.base.exception.CommandFailureException;
import com.delight.gaia.base.utility.HttpUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Slf4j
public class AuthenticationWebFilter extends DefaultAuthFilter<ServerHttpRequest, ServerHttpResponse> implements WebFilter {

    public AuthenticationWebFilter(SubjectHolder subjectHolder, GaiaSecurityManager securityManager) {
        super(subjectHolder, securityManager);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return afterProcess(chain.filter(exchange), doProcessAuth(exchange.getRequest(), exchange.getResponse())).then();

    }

    @Override
    public Token detectToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return new JwtToken().setToken(bearerToken.substring(7, bearerToken.length()));
        }
        return null;
    }

    @Override
    public String detectApp(ServerHttpRequest serverHttpRequest) {
        return serverHttpRequest.getHeaders().getFirst(CommonHeaders.APP);
    }

    @Override
    public void updateClientInfo(ServerHttpRequest request, ClientInfo clientInfo) {
        String deviceId = request.getHeaders().getFirst(CommonHeaders.DEVICE_ID);
        clientInfo.setDeviceId(deviceId);
        String platform = request.getHeaders().getFirst(CommonHeaders.PLATFORM);
        if (platform != null)
            try {
                clientInfo.setPlatform(Platform.valueOf(platform));
            } catch (Exception e) {
                log.error("convert header platform {} error ", platform, e);
                throw new CommandFailureException(MessageCode.INVALID_PARAMETER, "header platform");
            }
        clientInfo.setIp(HttpUtility.getIp(request));
    }
}
