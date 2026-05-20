package com.empresa.iogurtes.gestaoiogurtes.core.exception.ordemproducao;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class OrdemProducaoException extends BaseException {

    private final OrdemProducaoErrorCode errorCode;

    public OrdemProducaoException(OrdemProducaoErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    // Construtor com detalhe extra (ex: nome da matéria em falta)
    public OrdemProducaoException(OrdemProducaoErrorCode errorCode, String detalhe) {
        super(errorCode.getCode(), errorCode.getMessage() + ": " + detalhe);
        this.errorCode = errorCode;
    }

    public OrdemProducaoErrorCode getErrorCode() {
        return errorCode;
    }
}