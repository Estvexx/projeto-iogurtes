package com.empresa.iogurtes.gestaoiogurtes.core.exception.movimentos_stock;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class MovimentoStockMPException extends BaseException {

    private final MovimentoStockMPErrorCode errorCode;

    public MovimentoStockMPException(MovimentoStockMPErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MovimentoStockMPErrorCode getErrorCode() {
        return errorCode;
    }
}
