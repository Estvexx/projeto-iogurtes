package com.empresa.iogurtes.gestaoiogurtes.core.exception.user;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class UserException extends BaseException {

    private final UserErrorCode errorCode;

    public UserException(UserErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public UserErrorCode getErrorCode() {
        return errorCode;
    }
}
