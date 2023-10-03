package com.delight.gaia.base.constant;

import com.delight.gaia.base.vo.ResultCode;
import org.springframework.http.HttpStatus;

public interface MessageCode {
    ResultCode SUCCESS = new ResultCode(0, "success", HttpStatus.OK);
    ResultCode INTERNAL_SERVER_ERROR = new ResultCode(1, "error.systemError", HttpStatus.INTERNAL_SERVER_ERROR);
    ResultCode UNAUTHORIZED = new ResultCode(2, "error.unauthorized", HttpStatus.UNAUTHORIZED);
    ResultCode REQUIRED_LOGIN = new ResultCode(3, "error.requiredLogin", HttpStatus.FORBIDDEN);

    ResultCode PERMISSION_DENIED = new ResultCode(3, "error.forbidden", HttpStatus.FORBIDDEN);
    ResultCode MISSING_PARAMETER = new ResultCode(4, "error.missingParameter", HttpStatus.BAD_REQUEST);
    ResultCode INVALID_PARAMETER = new ResultCode(5, "error.invalidParameter", HttpStatus.BAD_REQUEST);
    ResultCode COMMON_INVALID_PARAMETER = new ResultCode(6, "error.commonInvalidParameter", HttpStatus.BAD_REQUEST);
    ResultCode TYPE_MISMATCH = new ResultCode(7, "error.typeMismatch", HttpStatus.BAD_REQUEST);
    ResultCode NOT_FOUND = new ResultCode(8, "error.notFound", HttpStatus.NOT_FOUND);
    ResultCode BAD_REQUEST = new ResultCode(9, "error.badRequest", HttpStatus.BAD_REQUEST);
    ResultCode NOT_SUPPORT_INVOICE_TYPE = new ResultCode(10, "error.notSupportInvoiceType", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    ResultCode MAX_LENGTH = new ResultCode(11, "error.maxLength", HttpStatus.BAD_REQUEST);
    ResultCode MIN_LENGTH = new ResultCode(12, "error.minLength", HttpStatus.BAD_REQUEST);
    ResultCode NOT_SUPPORT_MEDIA_TYPE = new ResultCode(13, "error.notSupportMediaType", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    ResultCode NOT_SUPPORT_METHOD = new ResultCode(14, "error.notSupportMethod", HttpStatus.METHOD_NOT_ALLOWED);
    ResultCode STALE_OBJECT_CONFLICT = new ResultCode(15, "error.stateObjectConflict", HttpStatus.CONFLICT);
    ResultCode CANT_LOCK = new ResultCode(16, "error.cantLock", HttpStatus.CONFLICT);
    ResultCode TOO_MANY_REQUEST = new ResultCode(17, "error.tooManyRequest", HttpStatus.TOO_MANY_REQUESTS);
    ResultCode LOGIC_ERROR = new ResultCode(18, "error.systemError", HttpStatus.OK);
    ResultCode NO_CONTENT = new ResultCode(19, "error.systemError", HttpStatus.OK);

}
