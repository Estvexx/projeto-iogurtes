package com.empresa.iogurtes.gestaoiogurtes.core.exception.validator;

public enum ValidationErrorCode {


    NOME_NULL("VAL_001", "Nome é obrigatório"),
    NOME_TOO_SHORT("VAL_002", "Nome deve ter pelo menos 4 caracteres"),
    NOME_TOO_LONG("VAL_003", "Nome não pode ter mais de 60 caracteres"),
    NOME_EMPRESA_NULL("VAL_021", "Nome da empresa é obrigatório"),
    NOME_EMPRESA_TOO_SHORT("VAL_022", "Nome da empresa deve ter pelo menos 4 caracteres"),
    NOME_EMPRESA_TOO_LONG("VAL_023", "Nome da empresa não pode ter mais de 150 caracteres"),

    EMAIL_NULL("VAL_004", "Email é obrigatório"),
    EMAIL_INVALID_FORMAT("VAL_005", "Formato de email inválido"),
    EMAIL_ALREADY_EXISTS("VAL_006", "Email já existe"),

    PASSWORD_NULL("VAL_007", "Password é obrigatória"),
    PASSWORD_TOO_SHORT("VAL_008", "Password deve ter pelo menos 8 caracteres"),
    PASSWORD_NO_UPPERCASE("VAL_009", "Password deve ter pelo menos uma letra maiúscula"),
    PASSWORD_NO_LOWERCASE("VAL_010", "Password deve ter pelo menos uma letra minúscula"),
    PASSWORD_NO_DIGIT("VAL_011", "Password deve ter pelo menos um número"),
    PASSWORD_NO_SYMBOL("VAL_012", "Password deve ter pelo menos um símbolo especial"),

    TURNO_INVALID("VAL_013", "Turno inválido"),
    TURNO_REQUIRED("VAL_014", "Turno é obrigatório para funcionários"),

    ROLE_NULL("VAL_015", "Role é obrigatória"),
    ROLE_INVALID("VAL_016", "Role inválida"),
    ROLE_NOT_FOUND("VAL_017", "Role não encontrada"),

    EMPRESA_NULL("VAL_018", "Empresa é obrigatória para clientes"),
    EMPRESA_NOT_FOUND("VAL_019", "Empresa não encontrada"),

    DATA_ADMISSAO_FUTURE("VAL_020", "Data de admissão não pode ser no futuro"),

    NIPC_NULL("VAL_024", "NIPC é obrigatório"),
    NIPC_INVALID("VAL_025", "NIPC inválido"),
    NIPC_ALREADY_EXISTS("VAL_026", "NIPC já existe"),

    MORADA_TOO_LONG("VAL_027", "Morada não pode exceder 200 caracteres"),
    CODIGO_POSTAL_INVALID("VAL_028", "Código postal inválido. Formato: 1234-567"),
    CIDADE_TOO_LONG("VAL_029", "Cidade não pode exceder 100 caracteres"),
    TELEFONE_INVALID("VAL_030", "Telefone inválido"),

    NOME_FORNECEDOR_TIPO_NULL("VAL_043", "Nome do tipo de fornecedor é obrigatório"),
    NOME_FORNECEDOR_TIPO_TOO_SHORT("VAL_044", "Nome do tipo de fornecedor deve ter pelo menos 2 caracteres"),
    NOME_FORNECEDOR_TIPO_TOO_LONG("VAL_045", "Nome do tipo de fornecedor não pode ter mais de 80 caracteres"),
    NOME_FORNECEDOR_TIPO_ALREADY_EXISTS("VAL_046", "Já existe um tipo de fornecedor com este nome"),
    NOME_FORNECEDOR_TIPO_ALREADY_EXISTS_UPDATE("VAL_047", "Já existe outro tipo de fornecedor com este nome"),

    NOME_FORNECEDOR_NULL("VAL_031", "Nome do fornecedor é obrigatório"),
    NOME_FORNECEDOR_TOO_SHORT("VAL_032", "Nome do fornecedor deve ter pelo menos 2 caracteres"),
    NOME_FORNECEDOR_TOO_LONG("VAL_033", "Nome do fornecedor não pode ter mais de 150 caracteres"),

    CERTIFICACAO_NOT_FOUND("VAL_040", "Certificação não encontrada"),
    NOME_CERTIFICACAO_ALREADY_EXISTS("VAL_041", "Já existe uma certificação com este nome"),
    NOME_CERTIFICACAO_ALREADY_EXISTS_UPDATE("VAL_042", "Já existe outra certificação com este nome"),
    DESCRICAO_CERTIFICACAO_NULL("VAL_041", "Descrição é necessário"),
    DESCRICAO_CERTIFICACAO_TOO_SHORT("VAL_041", "Descrição deve ter pelo menos 10 caracteres"),
    DESCRICAO_CERTIFICACAO_TOO_LONG("VAL_041", "Descrição dete ter até 500 caracteres"),
    NIF_NULL("VAL_034", "NIF é obrigatório"),
    NIF_INVALID("VAL_035", "NIF inválido"),
    NIF_ALREADY_EXISTS("VAL_036", "NIF já existe"),

    EMAIL_FORNECEDOR_INVALID_FORMAT("VAL_037", "Formato de email do fornecedor inválido"),

    NOME_CERTIFICACAO_NULL("VAL_038", "Nome da certificação é obrigatório"),
    NOME_CERTIFICACAO_TOO_SHORT("VAL_039", "Nome da certificação deve ter pelo menos 2 caracteres"),
    NOME_CERTIFICACAO_TOO_LONG("VAL_040", "Nome da certificação não pode ter mais de 80 caracteres"),

    DATA_INICIO_NULL("VAL_048", "Data de início é obrigatória"),
    DATA_FIM_BEFORE_DATA_INICIO("VAL_049", "Data de fim não pode ser anterior à data de início"),

    TIPO_NOT_FOUND("VAL_50", "Tipo para fornecedor não encontrado");

    private final String code;
    private final String message;

    ValidationErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}