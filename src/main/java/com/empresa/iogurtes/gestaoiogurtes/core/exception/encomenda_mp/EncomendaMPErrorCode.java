package com.empresa.iogurtes.gestaoiogurtes.core.exception.encomenda_mp;

public enum EncomendaMPErrorCode {

    ENCOMENDA_MP_NOT_FOUND("EMP_001", "Encomenda de matéria prima não encontrada"),
    ENCOMENDA_MP_CREATE_FAILED("EMP_002", "Falha ao criar encomenda de matéria prima"),
    ENCOMENDA_MP_UPDATE_FAILED("EMP_003", "Falha ao atualizar encomenda de matéria prima"),
    ENCOMENDA_MP_CANCEL_FAILED("EMP_004", "Encomenda só pode ser cancelada nos estados 'enviada' ou 'confirmada'"),
    ENCOMENDA_MP_LOCKED("EMP_005", "Encomenda bloqueada — só é possível editar no estado 'enviada'"),
    ENCOMENDA_MP_ALREADY_RECEBIDA("EMP_006", "Encomenda já foi marcada como recebida"),
    ENCOMENDA_MP_CANCELADA("EMP_007", "Encomenda está cancelada"),
    LINHA_NOT_FOUND("EMP_008", "Linha de encomenda não encontrada"),
    LINHA_ALREADY_EXISTS("EMP_009", "Esta matéria prima já existe nesta encomenda"),
    MATERIA_NAO_FORNECIDA("EMP_010", "Esta matéria prima não é fornecida pelo fornecedor desta encomenda"),
    SEM_LINHAS("EMP_011", "Não é possível confirmar uma encomenda sem linhas"),
    TRANSICAO_ESTADO_INVALIDA("EMP_012", "Transição de estado inválida"),
    MOEDAS_DIFERENTES("EMP_013", "Todas as linhas da encomenda devem ter a mesma moeda");

    private final String code;
    private final String message;

    EncomendaMPErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}