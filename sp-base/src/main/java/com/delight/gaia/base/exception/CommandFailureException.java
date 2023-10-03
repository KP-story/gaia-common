package com.delight.gaia.base.exception;

import com.delight.gaia.base.vo.ResultCode;
import lombok.Getter;

public class CommandFailureException extends RuntimeException {
    @Getter
    private ResultCode resultCode;
    @Getter
    private String [] args;

    public CommandFailureException(ResultCode resultCode) {
        super(resultCode.toString());
        this.resultCode = resultCode;
    }

    public CommandFailureException(ResultCode resultCode, Throwable throwable) {
        super(throwable);
        this.resultCode = resultCode;
    }
    public CommandFailureException(ResultCode resultCode, String ...args) {
        super(resultCode.toString());
        this.resultCode = resultCode;
        this.args=args;
    }

    public CommandFailureException(ResultCode resultCode, Throwable throwable, String ...args) {
        super(throwable);
        this.resultCode = resultCode;
        this.args=args;

    }
}
