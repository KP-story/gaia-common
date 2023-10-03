package com.delight.gaia.base.message;

import com.delight.gaia.base.vo.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class HttpErrorResponse extends ResponseEntity<Message> {
    public HttpErrorResponse(HttpStatus status) {
        super(status);
    }

    public HttpErrorResponse(Message body, HttpStatus status) {
        super(body, status);
    }

}
