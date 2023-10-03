package com.delight.gaia.base.exception;

public class IllegalReturnClassException extends  RuntimeException {
    public IllegalReturnClassException() {
    }

    public IllegalReturnClassException(String message) {
        super(message);
    }

    public IllegalReturnClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalReturnClassException(Throwable cause) {
        super(cause);
    }

    public IllegalReturnClassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
