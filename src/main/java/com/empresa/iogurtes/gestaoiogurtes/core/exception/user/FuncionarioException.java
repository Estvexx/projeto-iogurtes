package com.empresa.iogurtes.gestaoiogurtes.core.exception.user;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class FuncionarioException extends BaseException {

    private final FuncionarioErrorCode errorCode;

    public FuncionarioException(FuncionarioErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public FuncionarioErrorCode getErrorCode() {
        return errorCode;
    }
}