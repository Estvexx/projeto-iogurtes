package com.empresa.iogurtes.gestaoiogurtes.core.dto.movimentostockmp;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoMP;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record MovimentoStockMPResponse(
        UUID id,
        UUID userId,
        String userNome,
        TipoMovimentoMP tipo,
        String observacao,
        List<MovimentoStockMPMateriaResponse> materias,
        LocalDateTime createdAt
) {}
