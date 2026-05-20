package com.empresa.iogurtes.gestaoiogurtes.core.dto.ordemproducao;

import java.math.BigDecimal;
import java.util.UUID;

public record ConsumoProducaoResponse(
        UUID id,
        UUID materiaId,
        String materiaNome,
        String materiaUnidade,
        BigDecimal quantidadeKg
) {}