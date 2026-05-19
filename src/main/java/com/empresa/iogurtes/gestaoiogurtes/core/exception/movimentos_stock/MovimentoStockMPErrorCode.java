package com.empresa.iogurtes.gestaoiogurtes.core.exception.movimentos_stock;

public enum MovimentoStockMPErrorCode {

    MOVIMENTO_NOT_FOUND("MSM_001", "Movimento de stock não encontrado"),
    MOVIMENTO_CREATE_FAILED("MSM_002", "Falha ao registar movimento de stock"),
    STOCK_INSUFICIENTE("MSM_003", "Stock insuficiente — não é possível produzir devido a falta de matéria prima");

    private final String code;
    private final String message;

    MovimentoStockMPErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}