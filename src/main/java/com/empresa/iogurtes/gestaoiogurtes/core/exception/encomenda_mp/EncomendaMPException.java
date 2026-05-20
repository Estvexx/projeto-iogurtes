package com.empresa.iogurtes.gestaoiogurtes.core.exception.encomenda_mp;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class EncomendaMPException extends BaseException {

    private final EncomendaMPErrorCode errorCode;

    public EncomendaMPException(EncomendaMPErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public EncomendaMPErrorCode getErrorCode() {
        return errorCode;
    }
}

