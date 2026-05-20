package com.empresa.iogurtes.gestaoiogurtes.core.exception.moeda;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class MoedaException extends BaseException {

    private final MoedaErrorCode errorCode;

    public MoedaException(MoedaErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MoedaErrorCode getErrorCode() {
        return errorCode;
    }
}
