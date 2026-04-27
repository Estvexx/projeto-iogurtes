package com.empresa.iogurtes.gestaoiogurtes.core.exception.empresa;

public enum EmpresaErrorCode {

    EMPRESA_NOT_FOUND("EMP_001", "Empresa não encontrada"),
    EMPRESA_INACTIVE("EMP_002", "Empresa inativa"),
    EMPRESA_CREATE_FAILED("EMP_003", "Falha ao criar empresa"),
    EMPRESA_UPDATE_FAILED("EMP_004", "Falha ao atualizar empresa"),
    EMPRESA_DELETE_FAILED("EMP_005", "Falha ao eliminar empresa"),
    NIPC_ALREADY_EXISTS("EMP_006", "NIPC já existe"),
    EMPRESA_HAS_CLIENTES("EMP_007", "Empresa tem clientes associados");


    private final String code;
    private final String message;

    EmpresaErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}