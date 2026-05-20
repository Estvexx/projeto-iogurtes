package com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima;


public enum MateriaPrimaErrorCode {

    MATERIA_PRIMA_NOT_FOUND("MP_001", "Matéria prima não encontrada"),
    MATERIA_PRIMA_INACTIVE("MP_002", "Matéria prima inativa"),
    MATERIA_PRIMA_CREATE_FAILED("MP_003", "Falha ao criar matéria prima"),
    MATERIA_PRIMA_UPDATE_FAILED("MP_004", "Falha ao atualizar matéria prima"),
    MATERIA_PRIMA_DELETE_FAILED("MP_005", "Falha ao eliminar matéria prima"),
    NOME_ALREADY_EXISTS("MP_006", "Nome de matéria prima já existe"),
    STOCK_NEGATIVO("MP_007", "Stock não pode ser negativo"),
    MATERIA_PRIMA_EM_USO("MATERIA_PRIMA_EM_USO", "Produtos ativos estão a ser produzidos com esta matéria prima");

    private final String code;
    private final String message;

    MateriaPrimaErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}