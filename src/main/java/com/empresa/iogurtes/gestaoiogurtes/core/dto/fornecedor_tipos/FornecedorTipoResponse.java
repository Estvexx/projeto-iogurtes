package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos;

import java.time.LocalDateTime;
import java.util.UUID;

public record FornecedorTipoResponse(
        UUID id,
        String nome,
        String descricao,
        boolean isActive,
        LocalDateTime createdAt
) {}