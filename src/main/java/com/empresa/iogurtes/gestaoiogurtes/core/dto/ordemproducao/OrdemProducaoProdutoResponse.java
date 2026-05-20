package com.empresa.iogurtes.gestaoiogurtes.core.dto.ordemproducao;

import java.math.BigDecimal;
import java.util.UUID;

public record OrdemProducaoProdutoResponse(
        UUID id,
        UUID produtoId,
        String produtoNome,
        String produtoSku,
        BigDecimal quantidadeKg
) {}