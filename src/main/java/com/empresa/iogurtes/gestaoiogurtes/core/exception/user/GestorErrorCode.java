package com.empresa.iogurtes.gestaoiogurtes.core.exception.user;

public enum GestorErrorCode {

    GESTOR_NOT_FOUND("GES_001", "Gestor não encontrado"),
    GESTOR_INACTIVE("GES_002", "Gestor inativo"),
    GESTOR_CREATE_FAILED("GES_003", "Falha ao criar gestor"),
    GESTOR_UPDATE_FAILED("GES_004", "Falha ao atualizar gestor"),
    GESTOR_DELETE_FAILED("GES_005", "Falha ao eliminar gestor"),
    EMAIL_ALREADY_EXISTS("GES_006", "Email já existe"),
    INVALID_PASSWORD("GES_007", "Password inválida"),
    INVALID_ROLE("GES_008", "Role inválida");

    private final String code;
    private final String message;

    GestorErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}