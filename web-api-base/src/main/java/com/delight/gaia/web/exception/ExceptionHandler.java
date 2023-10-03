package com.delight.gaia.web.exception;

import com.delight.gaia.base.message.GaiaMessage;
import com.delight.gaia.base.message.GaiaMessageResponse;
import com.delight.gaia.base.message.HttpErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public interface ExceptionHandler<T extends Throwable> extends Ordered {

    HttpErrorResponse handle(Locale locale, ServerWebExchange exchange, T ex);

    GaiaMessageResponse handle(Locale locale,GaiaMessage request, T ex) throws IOException;

    List<Class<? extends Throwable>> accept();
}
