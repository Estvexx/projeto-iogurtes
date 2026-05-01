package com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class CertificacaoException extends BaseException {

    private final CertificacaoErrorCode errorCode;

    public CertificacaoException(CertificacaoErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CertificacaoErrorCode getErrorCode() {
        return errorCode;
    }
}