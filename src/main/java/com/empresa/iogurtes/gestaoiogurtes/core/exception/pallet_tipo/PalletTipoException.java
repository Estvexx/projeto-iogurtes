package com.empresa.iogurtes.gestaoiogurtes.core.exception.pallet_tipo;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class PalletTipoException extends BaseException {
    public PalletTipoException(PalletTipoErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
    }
}