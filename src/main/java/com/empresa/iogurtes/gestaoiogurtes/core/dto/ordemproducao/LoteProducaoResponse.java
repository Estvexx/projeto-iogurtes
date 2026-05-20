package com.empresa.iogurtes.gestaoiogurtes.core.dto.ordemproducao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record LoteProducaoResponse(
        UUID id,
        UUID ordemId,
        UUID produtoId,
        String produtoNome,
        String produtoSku,
        String numeroLote,
        BigDecimal quantidadeKg,
        BigDecimal stockAtualKg,
        LocalDate dataProducao,
        LocalDate dataValidade,
        boolean isActive,
        LocalDateTime createdAt
) {}