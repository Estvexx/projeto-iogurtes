package com.empresa.iogurtes.gestaoiogurtes.core.exception.lote_producao;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class LoteProducaoException extends BaseException {

    private final LoteProducaoErrorCode errorCode;

    public LoteProducaoException(LoteProducaoErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public LoteProducaoErrorCode getErrorCode() { return errorCode; }
}