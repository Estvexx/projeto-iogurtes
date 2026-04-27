package com.empresa.iogurtes.gestaoiogurtes.core.exception.user;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class ClienteException extends BaseException {

    private final ClienteErrorCode errorCode;

    public ClienteException(ClienteErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ClienteErrorCode getErrorCode() {
        return errorCode;
    }
}