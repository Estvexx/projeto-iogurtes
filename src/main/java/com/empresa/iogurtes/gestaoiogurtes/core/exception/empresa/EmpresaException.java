package com.empresa.iogurtes.gestaoiogurtes.core.exception.empresa;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class EmpresaException extends BaseException {

    private final EmpresaErrorCode errorCode;

    public EmpresaException(EmpresaErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public EmpresaErrorCode getErrorCode() {
        return errorCode;
    }
}