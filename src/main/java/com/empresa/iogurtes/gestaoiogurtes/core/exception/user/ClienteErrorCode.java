package com.empresa.iogurtes.gestaoiogurtes.core.exception.user;

public enum ClienteErrorCode {

    CLIENTE_NOT_FOUND("CLI_001", "Cliente não encontrado"),
    CLIENTE_INACTIVE("CLI_002", "Cliente inativo"),
    CLIENTE_CREATE_FAILED("CLI_003", "Falha ao criar cliente"),
    CLIENTE_UPDATE_FAILED("CLI_004", "Falha ao atualizar cliente"),
    CLIENTE_DELETE_FAILED("CLI_005", "Falha ao eliminar cliente"),
    EMPRESA_REQUIRED("CLI_006", "Clientes requerem uma empresa associada"),
    EMAIL_ALREADY_EXISTS("CLI_007", "Email já existe"),
    INVALID_PASSWORD("CLI_008", "Password inválida"),
    INVALID_ROLE("CLI_009", "Role inválida");

    private final String code;
    private final String message;

    ClienteErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}