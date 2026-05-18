package com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor;

public enum FornecedorErrorCode {

    FORNECEDOR_NOT_FOUND("FORN_001", "Fornecedor não encontrado"),
    FORNECEDOR_INACTIVE("FORN_002", "Fornecedor inativo"),
    FORNECEDOR_CREATE_FAILED("FORN_003", "Falha ao criar fornecedor"),
    FORNECEDOR_UPDATE_FAILED("FORN_004", "Falha ao atualizar fornecedor"),
    FORNECEDOR_DELETE_FAILED("FORN_005", "Falha ao eliminar fornecedor"),
    FORNECEDOR_HAS_ENCOMENDAS_PENDENTES("FORN_006", "Fornecedor tem encomendas pendentes"),
    NIF_ALREADY_EXISTS("FORN_007", "NIF já existe");

    private final String code;
    private final String message;

    FornecedorErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}