package com.empresa.iogurtes.gestaoiogurtes.core.exception.user;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class AdminException extends BaseException {

    private final AdminErrorCode errorCode;

    public AdminException(AdminErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AdminErrorCode getErrorCode() {
        return errorCode;
    }
}