package com.delight.gaia.core.messaging.server.rsocket;

import com.delight.gaia.base.message.GaiaMessageCodec;
import com.delight.gaia.core.messaging.GaiaMessageExceptionHandler;
import com.delight.gaia.core.messaging.MessageHandler;
import com.delight.gaia.core.messaging.server.config.ServerSocketConfig;
import io.netty.buffer.ByteBuf;
import io.rsocket.Payload;
import io.rsocket.core.RSocketServer;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.TcpServerTransport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.time.Duration;
@Slf4j
public class RsServer implements DisposableBean {
    private CloseableChannel channel;
    private final MessageHandler messageHandler;
    @Autowired
    private  GaiaMessageExceptionHandler gaiaMessageExceptionHandler;

    public RsServer(MessageHandler messageHandler, ServerSocketConfig serverSocketConfig, GaiaMessageCodec<Payload, ByteBuf> codec) {
        this.messageHandler = messageHandler;
        start(serverSocketConfig, codec);
    }

    public void start(ServerSocketConfig serverSocketConfig, GaiaMessageCodec<Payload, ByteBuf> codec) {
        var mono = RSocketServer.create((setup, sendingSocket) -> Mono.just(new RsSocketSession(codec, sendingSocket, messageHandler,gaiaMessageExceptionHandler)))
                .payloadDecoder(PayloadDecoder.ZERO_COPY)
                .bind(TcpServerTransport.create(serverSocketConfig.getAddress().getHost(), serverSocketConfig.getAddress().getPort()));
        channel = mono.block(Duration.ofMillis(serverSocketConfig.getInitTimeout()));
        log.info("RsServer started in port: "+serverSocketConfig.getAddress().getPort());
        startDaemonAwaitThread(channel);
    }

    private void startDaemonAwaitThread(CloseableChannel channel) {
        Thread awaitThread = new Thread(() -> channel.onClose().block(), "rsocket");
        awaitThread.setContextClassLoader(getClass().getClassLoader());
        awaitThread.setDaemon(true);
        awaitThread.start();
    }


    public void stop() {
        if (this.channel != null) {
            this.channel.dispose();
            this.channel = null;
        }
    }

    @Override
    public void destroy() throws Exception {
        stop();
    }
}
