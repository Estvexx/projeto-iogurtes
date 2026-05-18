package com.empresa.iogurtes.gestaoiogurtes.core.exception.user;

public enum UserErrorCode {

    USER_NOT_FOUND("USR_001", "Utilizador não encontrado"),
    USER_DELETE_FAILED("USR_002", "Erro ao eliminar utilizador!");


    private final String code;
    private final String message;

    UserErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}