package com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima;

public enum TipoMateriaErrorCode {
    TIPO_MATERIA_NOT_FOUND("TIPO_MATERIA_NOT_FOUND", "Tipo de matéria-prima não encontrado"),
    TIPO_MATERIA_CREATE_FAILED("TIPO_MATERIA_CREATE_FAILED", "Falha ao criar tipo de matéria-prima"),
    TIPO_MATERIA_UPDATE_FAILED("TIPO_MATERIA_UPDATE_FAILED", "Falha ao atualizar tipo de matéria-prima"),
    TIPO_MATERIA_DELETE_FAILED("TIPO_MATERIA_DELETE_FAILED", "Falha ao eliminar tipo de matéria-prima"),
    TIPO_MATERIA_EM_USO("TIPO_MATERIA_EM_USO", "Tipo de matéria-prima está em uso e não pode ser eliminado"),
    NOME_ALREADY_EXISTS("NOME_TIPO_MATERIA_ALREADY_EXISTS", "Já existe um tipo com este nome");

    private final String code;
    private final String message;

    TipoMateriaErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}