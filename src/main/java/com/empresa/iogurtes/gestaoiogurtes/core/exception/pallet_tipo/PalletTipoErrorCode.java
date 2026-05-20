package com.empresa.iogurtes.gestaoiogurtes.core.exception.pallet_tipo;

public enum PalletTipoErrorCode {
    PALLET_TIPO_NOT_FOUND("PALLET_TIPO_NOT_FOUND", "Tipo de pallet não encontrado"),
    PALLET_TIPO_CREATE_FAILED("PALLET_TIPO_CREATE_FAILED", "Falha ao criar tipo de pallet"),
    PALLET_TIPO_UPDATE_FAILED("PALLET_TIPO_UPDATE_FAILED", "Falha ao atualizar tipo de pallet"),
    PALLET_TIPO_DELETE_FAILED("PALLET_TIPO_DELETE_FAILED", "Falha ao eliminar tipo de pallet"),
    NOME_ALREADY_EXISTS("NOME_PALLET_TIPO_ALREADY_EXISTS", "Já existe um tipo de pallet com este nome"),
    CAPACIDADE_ALREADY_EXISTS("CAPACIDADE_PALLET_TIPO_ALREADY_EXISTS", "Já existe um tipo de pallet com esta capacidade"),
    PALLET_TIPO_ALREADY_ACTIVE("PALLET_TIPO_ALREADY_ACTIVE", "O tipo de pallet já está ativo");

    private final String code;
    private final String message;

    PalletTipoErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}