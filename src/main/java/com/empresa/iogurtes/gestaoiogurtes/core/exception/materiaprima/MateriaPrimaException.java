package com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class MateriaPrimaException extends BaseException {

    private final MateriaPrimaErrorCode errorCode;

    public MateriaPrimaException(MateriaPrimaErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MateriaPrimaErrorCode getErrorCode() {
        return errorCode;
    }
}
