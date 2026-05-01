package com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor;

public enum CertificacaoErrorCode {

    CERTIFICACAO_NOT_FOUND("CERT_001", "Certificação não encontrada"),
    CERTIFICACAO_INACTIVE("CERT_002", "Certificação inativa"),
    CERTIFICACAO_CREATE_FAILED("CERT_003", "Falha ao criar certificação"),
    CERTIFICACAO_UPDATE_FAILED("CERT_004", "Falha ao atualizar certificação"),
    CERTIFICACAO_DELETE_FAILED("CERT_005", "Falha ao eliminar certificação");

    private final String code;
    private final String message;

    CertificacaoErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}