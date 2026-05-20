package com.empresa.iogurtes.gestaoiogurtes.core.dto.moeda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MoedaResponse(
        UUID id,
        String codigo,
        String nome,
        String simbolo,
        BigDecimal taxaConversaoEur,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
