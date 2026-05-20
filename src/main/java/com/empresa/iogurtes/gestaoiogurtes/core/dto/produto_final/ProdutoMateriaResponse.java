package com.empresa.iogurtes.gestaoiogurtes.core.dto.produto_final;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoMateriaResponse(
        UUID id,
        UUID materiaId,
        String materiaNome,
        String materiaUnidade,
        BigDecimal quantidadePorUnidadeProduto
) {}