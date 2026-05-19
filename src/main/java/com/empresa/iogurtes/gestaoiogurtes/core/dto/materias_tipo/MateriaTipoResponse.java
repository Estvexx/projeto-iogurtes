package com.empresa.iogurtes.gestaoiogurtes.core.dto.materias_tipo;

import java.time.LocalDateTime;
import java.util.UUID;

public record MateriaTipoResponse (
    UUID id,
    String nome,
    String descricao,
    boolean isActive,
    LocalDateTime createdAt
) {}
