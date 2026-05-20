package com.empresa.iogurtes.gestaoiogurtes.core.dto.pallet_tipo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PalletTipoResponse(
        UUID id,
        String nome,
        BigDecimal capacidadeKg,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}