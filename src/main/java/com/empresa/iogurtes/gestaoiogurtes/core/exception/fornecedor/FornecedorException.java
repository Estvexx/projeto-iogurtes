package com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class FornecedorException extends BaseException {

    private final FornecedorErrorCode errorCode;

    public FornecedorException(FornecedorErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public FornecedorErrorCode getErrorCode() {
        return errorCode;
    }
}