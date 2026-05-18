package com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class FornecedorCertificacaoException extends BaseException {

    private final FornecedorCertificacaoErrorCode errorCode;

    public FornecedorCertificacaoException(FornecedorCertificacaoErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public FornecedorCertificacaoErrorCode getErrorCode() {
        return errorCode;
    }
}