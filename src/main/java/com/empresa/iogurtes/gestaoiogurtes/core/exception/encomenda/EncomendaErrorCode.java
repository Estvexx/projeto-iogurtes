package com.empresa.iogurtes.gestaoiogurtes.core.exception.encomenda;

public enum EncomendaErrorCode {

    ENCOMENDA_NOT_FOUND("ENC_001", "Encomenda não encontrada"),
    ENCOMENDA_CREATE_FAILED("ENC_002", "Falha ao criar encomenda"),
    ENCOMENDA_CANCEL_FAILED("ENC_003", "Só é possível cancelar encomendas no estado 'pendente'"),
    ENCOMENDA_JA_EXPEDIDA("ENC_004", "Encomenda já foi expedida"),
    ENCOMENDA_CANCELADA("ENC_005", "Encomenda está cancelada"),
    TRANSICAO_ESTADO_INVALIDA("ENC_006", "Transição de estado inválida"),
    SEM_PALLETS("ENC_007", "Encomenda deve ter pelo menos uma pallet"),
    PALLET_TIPO_NOT_FOUND("ENC_008", "Tipo de pallet não encontrado"),
    PRODUTO_SEM_PRECO_KG("ENC_009", "Produto não tem preço por kg definido"),
    ENCOMENDA_ORDEM_NOT_FOUND("ENC_010", "Encomenda ordem não encontrada"),
    ENCOMENDA_TRANSICAO_INVALIDA("ENC_011", "Transição de estado inválida para esta encomenda"),
    ENCOMENDA_ORDENS_NAO_CONCLUIDAS("ENC_012", "Existem ordens de produção associadas não concluídas"),
    ENCOMENDA_ORDEM_JA_PROCESSADA("ENC_013", "Encomenda ordem já foi aceite ou recusada");

    private final String code;
    private final String message;

    EncomendaErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}