package com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor;

public enum FornecedorTipoErrorCode {

    FORNECEDOR_TIPO_NOT_FOUND("FORN_TIPO_001", "Tipo de fornecedor não encontrado"),
    FORNECEDOR_TIPO_INACTIVE("FORN_TIPO_002", "Tipo de fornecedor inativo"),
    FORNECEDOR_TIPO_CREATE_FAILED("FORN_TIPO_003", "Falha ao criar tipo de fornecedor"),
    FORNECEDOR_TIPO_UPDATE_FAILED("FORN_TIPO_004", "Falha ao atualizar tipo de fornecedor"),
    FORNECEDOR_TIPO_DELETE_FAILED("FORN_TIPO_005", "Falha ao eliminar tipo de fornecedor"),
    FORNECEDOR_TIPO_HAS_FORNECEDORES("FORN_TIPO_006", "Tipo de fornecedor tem fornecedores associados");

    private final String code;
    private final String message;

    FornecedorTipoErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}