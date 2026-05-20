package com.empresa.iogurtes.gestaoiogurtes.core.dto.movimentostockmp;

import java.math.BigDecimal;
import java.util.UUID;

public record MovimentoStockMPMateriaResponse(
        UUID id,
        UUID materiaId,
        String materiaNome,
        String materiaUnidade,
        BigDecimal quantidade
) {}
