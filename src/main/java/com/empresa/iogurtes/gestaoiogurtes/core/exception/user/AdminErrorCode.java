package com.empresa.iogurtes.gestaoiogurtes.core.exception.user;

public enum AdminErrorCode {

    ADMIN_NOT_FOUND("ADM_001", "Admin não encontrado"),
    ADMIN_INACTIVE("ADM_002", "Admin inativo"),
    ADMIN_CREATE_FAILED("ADM_003", "Falha ao criar admin"),
    ADMIN_UPDATE_FAILED("ADM_004", "Falha ao atualizar admin"),
    ADMIN_DELETE_FAILED("ADM_005", "Falha ao eliminar admin"),
    EMAIL_ALREADY_EXISTS("ADM_006", "Email já existe"),
    INVALID_PASSWORD("ADM_007", "Password inválida"),
    INVALID_ROLE("ADM_008", "Role inválida");

    private final String code;
    private final String message;

    AdminErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}