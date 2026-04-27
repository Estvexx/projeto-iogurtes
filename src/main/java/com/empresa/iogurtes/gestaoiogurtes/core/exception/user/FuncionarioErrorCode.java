package com.empresa.iogurtes.gestaoiogurtes.core.exception.user;

public enum FuncionarioErrorCode {

    FUNCIONARIO_NOT_FOUND("FUNC_001", "Funcionário não encontrado"),
    FUNCIONARIO_INACTIVE("FUNC_002", "Funcionário inativo"),
    FUNCIONARIO_CREATE_FAILED("FUNC_003", "Falha ao criar funcionário"),
    FUNCIONARIO_UPDATE_FAILED("FUNC_004", "Falha ao atualizar funcionário"),
    FUNCIONARIO_DELETE_FAILED("FUNC_005", "Falha ao eliminar funcionário"),
    TURNO_REQUIRED("FUNC_006", "Funcionários requerem turno"),
    INVALID_TURNO("FUNC_007", "Turno inválido"),
    EMAIL_ALREADY_EXISTS("FUNC_008", "Email já existe"),
    INVALID_PASSWORD("FUNC_009", "Password inválida"),
    INVALID_ROLE("FUNC_010", "Role inválida");

    private final String code;
    private final String message;

    FuncionarioErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}