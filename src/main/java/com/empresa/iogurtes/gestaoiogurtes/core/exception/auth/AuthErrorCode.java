package com.empresa.iogurtes.gestaoiogurtes.core.exception.auth;

public enum AuthErrorCode {

    INVALID_CREDENTIALS(
            "AUTH_INVALID_CREDENTIALS",
            "Email ou password inválidos"
    ),

    USER_INACTIVE(
            "AUTH_USER_INACTIVE",
            "O utilizador está inativo"
    );

    private final String code;
    private final String message;

    AuthErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}