package com.empresa.iogurtes.gestaoiogurtes.core.exception.lote_producao;

public enum LoteProducaoErrorCode {

    LOTE_NOT_FOUND("LOTE_001", "Lote de produção não encontrado"),
    LOTE_INACTIVE("LOTE_002", "Lote de produção inativo");

    private final String code;
    private final String message;

    LoteProducaoErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}