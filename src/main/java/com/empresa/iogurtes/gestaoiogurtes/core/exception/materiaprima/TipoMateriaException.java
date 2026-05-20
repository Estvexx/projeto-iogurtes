package com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class TipoMateriaException extends BaseException {
    private final TipoMateriaErrorCode errorCode;

    public TipoMateriaException(TipoMateriaErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public TipoMateriaErrorCode getErrorCode() {
        return errorCode;
    }
}