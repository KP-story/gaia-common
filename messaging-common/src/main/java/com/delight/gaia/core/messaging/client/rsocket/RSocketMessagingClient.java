package com.delight.gaia.core.messaging.client.rsocket;

import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageCodec;
import com.delight.gaia.base.vo.RemoteAddr;
import com.delight.gaia.core.messaging.client.ClientSocketConfig;
import com.delight.gaia.core.messaging.client.RSocketSession;
import io.netty.buffer.ByteBuf;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.loadbalance.*;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.beans.factory.DisposableBean;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RSocketMessagingClient extends RSocketSession implements DisposableBean {
    private RSocketClient rsocketClient;

    public RSocketMessagingClient(GaiaMessageCodec<Payload, ByteBuf> codec, ClientSocketConfig clientSocketConfig) {
        super(codec);
        init(clientSocketConfig);
    }

    public void init(ClientSocketConfig clientSocketConfig) {
        List<RemoteAddr> servers = clientSocketConfig.getServers();
        RSocketConnector rSocketConnector = RSocketConnector.create()
                .metadataMimeType("message/x.rsocket.composite-metadata.v0").payloadDecoder(PayloadDecoder.ZERO_COPY)
                .dataMimeType("application/cbor");
        if (clientSocketConfig.getRetryBackoff() != null)
            rSocketConnector = rSocketConnector.reconnect(Retry.backoff(clientSocketConfig.getRetryBackoff().getMaxAttempts(), Duration.ofSeconds(clientSocketConfig.getRetryBackoff().getMinBackoffSeconds())));
        if (servers.size() == 1) {
            RemoteAddr remoteAddr = servers.get(0);
            Mono<RSocket> source =
                    rSocketConnector.connect(TcpClientTransport.create(remoteAddr.getHost(), remoteAddr.getPort()));
            rsocketClient = RSocketClient.from(source);
        } else {
            List<LoadbalanceTarget> loadbalanceTargets = new ArrayList(servers.size());
            for (RemoteAddr remoteAddr : servers) {
                LoadbalanceTarget target = LoadbalanceTarget.from(remoteAddr.getHost(), TcpClientTransport.create(remoteAddr.getHost(), remoteAddr.getPort()));
                loadbalanceTargets.add(target);
            }
            LoadbalanceStrategy loadbalanceStrategy;
            if (clientSocketConfig.getLbStrategy().equals("ROUND_ROBIN")) {
                loadbalanceStrategy = new RoundRobinLoadbalanceStrategy();
            } else {
                loadbalanceStrategy = WeightedLoadbalanceStrategy.create();
            }

            rsocketClient = LoadbalanceRSocketClient.builder(Mono.just(loadbalanceTargets)).connector(rSocketConnector).loadbalanceStrategy(loadbalanceStrategy).build();
        }

    }


    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean isOpen() {
        return !rsocketClient.isDisposed();
    }

    @Override
    public Mono<GaiaMessage> requestResponse(Mono<GaiaMessage> request) {
        return decodePayload(rsocketClient.requestResponse(buildPayload(request)));
    }


    @Override
    public Mono<Void> fireAndForget(Mono<GaiaMessage> request) {
        return rsocketClient.fireAndForget(buildPayload(request));
    }

    @Override
    public Mono<Void> fireAndForget(GaiaMessage request) {
        return null;
    }


    @Override
    public Mono<Void> close() {
        return Mono.fromRunnable(() -> rsocketClient.dispose());
    }

    @Override
    public void destroy() throws Exception {
        this.rsocketClient = null;
        close().block();
    }
}
