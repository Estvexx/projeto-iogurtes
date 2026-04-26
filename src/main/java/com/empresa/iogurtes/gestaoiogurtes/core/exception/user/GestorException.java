package com.empresa.iogurtes.gestaoiogurtes.core.exception.user;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class GestorException extends BaseException {

    private final GestorErrorCode errorCode;

    public GestorException(GestorErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public GestorErrorCode getErrorCode() {
        return errorCode;
    }
}