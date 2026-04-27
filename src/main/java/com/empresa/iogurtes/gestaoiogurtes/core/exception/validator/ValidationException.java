package com.empresa.iogurtes.gestaoiogurtes.core.exception.validator;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.BaseException;

public class ValidationException extends BaseException {

    private final ValidationErrorCode errorCode;

    public ValidationException(ValidationErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ValidationErrorCode getErrorCode() {
        return errorCode;
    }
}