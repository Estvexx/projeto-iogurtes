package com.empresa.iogurtes.gestaoiogurtes.core.exception.encomenda;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class EncomendaException extends BaseException {

    private final EncomendaErrorCode errorCode;

    public EncomendaException(EncomendaErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public EncomendaException(EncomendaErrorCode errorCode, String detalhe) {
        super(errorCode.getCode(), errorCode.getMessage() + ": " + detalhe);
        this.errorCode = errorCode;
    }

    public EncomendaErrorCode getErrorCode() {
        return errorCode;
    }
}