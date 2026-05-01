package com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class FornecedorTipoException extends BaseException {

    private final FornecedorTipoErrorCode errorCode;

    public FornecedorTipoException(FornecedorTipoErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public FornecedorTipoErrorCode getErrorCode() {
        return errorCode;
    }
}