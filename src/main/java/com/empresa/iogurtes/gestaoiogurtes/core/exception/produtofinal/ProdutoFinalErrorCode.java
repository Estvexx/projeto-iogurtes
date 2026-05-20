package com.empresa.iogurtes.gestaoiogurtes.core.exception.produtofinal;

public enum ProdutoFinalErrorCode {

    PRODUTO_FINAL_NOT_FOUND("PF_001", "Produto final não encontrado"),
    PRODUTO_FINAL_INACTIVE("PF_002", "Produto final inativo"),
    PRODUTO_FINAL_CREATE_FAILED("PF_003", "Falha ao criar produto final"),
    PRODUTO_FINAL_UPDATE_FAILED("PF_004", "Falha ao atualizar produto final"),
    PRODUTO_FINAL_DELETE_FAILED("PF_005", "Falha ao eliminar produto final"),
    NOME_ALREADY_EXISTS("PF_006", "Nome de produto final já existe"),
    COMPOSICAO_OBRIGATORIA("PF_007", "Produto final deve ter pelo menos uma matéria prima na composição"),
    MATERIA_DUPLICADA_COMPOSICAO("PF_008", "Composição contém matérias primas duplicadas"),
    PRODUTO_MATERIA_NOT_FOUND("PF_009", "Linha de composição não encontrada"),
    MATERIA_JA_NA_COMPOSICAO("PF_010", "Esta matéria prima já existe na composição deste produto");

    private final String code;
    private final String message;

    ProdutoFinalErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}
