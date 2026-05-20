package com.empresa.iogurtes.gestaoiogurtes.core.exception.ordemproducao;

public enum OrdemProducaoErrorCode {

    ORDEM_NOT_FOUND("OP_001", "Ordem de produção não encontrada"),
    ORDEM_CREATE_FAILED("OP_002", "Falha ao criar ordem de produção"),
    ORDEM_UPDATE_FAILED("OP_003", "Falha ao atualizar ordem de produção"),
    ORDEM_CANCEL_FAILED("OP_004", "Ordem só pode ser cancelada no estado 'em produção'"),
    ORDEM_CONCLUIR_FAILED("OP_005", "Ordem só pode ser concluída no estado 'em produção'"),
    ORDEM_JA_CONCLUIDA("OP_006", "Ordem já foi concluída"),
    ORDEM_CANCELADA("OP_007", "Ordem está cancelada"),
    TRANSICAO_ESTADO_INVALIDA("OP_008", "Transição de estado inválida"),
    SEM_PRODUTOS("OP_009", "Ordem de produção deve ter pelo menos um produto"),
    PRODUTO_DUPLICADO("OP_010", "Ordem contém produtos duplicados"),
    STOCK_INSUFICIENTE("OP_011", "Stock insuficiente de matéria prima"),
    PRODUTO_SEM_COMPOSICAO("OP_012", "Produto não tem composição definida");

    private final String code;
    private final String message;

    OrdemProducaoErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}