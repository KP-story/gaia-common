package com.delight.gaia.core.messaging.server.route;

public class RouteDuplicateException extends RuntimeException {
    public RouteDuplicateException() {
    }

    public RouteDuplicateException(String message) {
        super(message);
    }

    public RouteDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouteDuplicateException(Throwable cause) {
        super(cause);
    }

    public RouteDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
