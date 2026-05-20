package com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class MateriaFornecedorException extends BaseException {

    private final com.empresa.iogurtes.gestaoiogurtes.core.exception.materiafornecedor.MateriaFornecedorErrorCode errorCode;

    public MateriaFornecedorException(com.empresa.iogurtes.gestaoiogurtes.core.exception.materiafornecedor.MateriaFornecedorErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public com.empresa.iogurtes.gestaoiogurtes.core.exception.materiafornecedor.MateriaFornecedorErrorCode getErrorCode() {
        return errorCode;
    }
}