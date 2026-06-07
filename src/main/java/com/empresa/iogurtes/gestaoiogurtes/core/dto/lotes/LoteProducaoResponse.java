package com.empresa.iogurtes.gestaoiogurtes.core.dto.lotes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record LoteProducaoResponse(
        UUID id,
        UUID ordemId,
        UUID produtoId,
        String produtoNome,
        String numeroLote,
        BigDecimal quantidadeKg,
        BigDecimal stockAtualKg,
        String estado,
        LocalDate dataProducao,
        LocalDate dataValidade,
        boolean isActive,
        LocalDateTime createdAt
) {}