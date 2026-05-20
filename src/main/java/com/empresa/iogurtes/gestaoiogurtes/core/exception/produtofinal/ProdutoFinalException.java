package com.empresa.iogurtes.gestaoiogurtes.core.exception.produtofinal;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class ProdutoFinalException extends BaseException {

    private final ProdutoFinalErrorCode errorCode;

    public ProdutoFinalException(ProdutoFinalErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ProdutoFinalErrorCode getErrorCode() {
        return errorCode;
    }
}