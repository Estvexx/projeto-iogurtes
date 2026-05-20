package com.empresa.iogurtes.gestaoiogurtes.core.dto.encomendamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record EncomendaMPLinhaResponse(
        UUID id,
        UUID materiaId,
        String materiaNome,
        String materiaUnidade,
        BigDecimal quantidade,
        BigDecimal precoUnitario,
        BigDecimal precoUnitarioEur,
        BigDecimal taxaIva,
        BigDecimal subtotal,
        BigDecimal subtotalEur,
        LocalDateTime createdAt
) {}
