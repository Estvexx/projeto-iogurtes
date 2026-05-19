package com.empresa.iogurtes.gestaoiogurtes.core.exception.moeda;

public enum MoedaErrorCode {

    MOEDA_NOT_FOUND("MOE_001", "Moeda não encontrada"),
    MOEDA_INACTIVE("MOE_002", "Moeda inativa"),
    MOEDA_CREATE_FAILED("MOE_003", "Falha ao criar moeda"),
    MOEDA_UPDATE_FAILED("MOE_004", "Falha ao atualizar moeda"),
    MOEDA_DELETE_FAILED("MOE_005", "Falha ao eliminar moeda"),
    CODIGO_ALREADY_EXISTS("MOE_006", "Código de moeda já existe"),
    MOEDA_BASE_IMMUTABLE("MOE_007", "A moeda base EUR não pode ser alterada nem eliminada"),
    MOEDA_EM_USO("MOE_008", "Moeda está em uso e não pode ser eliminada");

    private final String code;
    private final String message;

    MoedaErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}