package com.empresa.iogurtes.gestaoiogurtes.core.exception.materiafornecedor;

public enum MateriaFornecedorErrorCode {

    MATERIA_FORNECEDOR_NOT_FOUND("MF_001", "Associação matéria-fornecedor não encontrada"),
    MATERIA_FORNECEDOR_INACTIVE("MF_002", "Associação matéria-fornecedor inativa"),
    MATERIA_FORNECEDOR_CREATE_FAILED("MF_003", "Falha ao criar associação matéria-fornecedor"),
    MATERIA_FORNECEDOR_UPDATE_FAILED("MF_004", "Falha ao atualizar associação matéria-fornecedor"),
    MATERIA_FORNECEDOR_DELETE_FAILED("MF_005", "Falha ao eliminar associação matéria-fornecedor"),
    ASSOCIACAO_ALREADY_EXISTS("MF_006", "Este fornecedor já está associado a esta matéria prima"),
    FORNECEDOR_INACTIVE("MF_007", "Fornecedor está inativo"),
    MOEDA_INACTIVE("MF_008", "Moeda está inativa");

    private final String code;
    private final String message;

    MateriaFornecedorErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}
