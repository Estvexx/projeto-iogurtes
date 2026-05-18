package com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor;

public enum FornecedorCertificacaoErrorCode {

    FORNECEDOR_CERTIFICACAO_NOT_FOUND("FORN_CERT_001", "Certificação do fornecedor não encontrada"),
    FORNECEDOR_CERTIFICACAO_ALREADY_EXISTS("FORN_CERT_002", "Fornecedor já tem esta certificação associada"),
    FORNECEDOR_CERTIFICACAO_CREATE_FAILED("FORN_CERT_003", "Falha ao associar certificação ao fornecedor"),
    FORNECEDOR_CERTIFICACAO_UPDATE_FAILED("FORN_CERT_004", "Falha ao atualizar certificação do fornecedor"),
    FORNECEDOR_CERTIFICACAO_DELETE_FAILED("FORN_CERT_005", "Falha ao remover certificação do fornecedor"),
    DATA_FIM_BEFORE_DATA_INICIO("FORN_CERT_006", "Data de fim não pode ser anterior à data de início");

    private final String code;
    private final String message;

    FornecedorCertificacaoErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}