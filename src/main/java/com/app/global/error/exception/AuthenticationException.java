package com.app.global.error.exception;

import com.app.global.error.ErrorCode;
import lombok.Getter;

public class AuthenticationException extends BusinessException{

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
