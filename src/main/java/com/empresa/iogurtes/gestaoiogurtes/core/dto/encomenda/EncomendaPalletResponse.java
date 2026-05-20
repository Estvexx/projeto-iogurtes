
package com.empresa.iogurtes.gestaoiogurtes.core.dto.encomenda;

import java.math.BigDecimal;
import java.util.UUID;

public record EncomendaPalletResponse(
        UUID id,
        UUID produtoId,
        String produtoNome,
        String produtoSku,
        UUID palletTipoId,
        String palletTipoNome,
        BigDecimal palletCapacidadeKg,
        Integer quantidadePallets,
        BigDecimal precoPorPalletEur,
        BigDecimal taxaIva,
        BigDecimal subtotalEur,
        BigDecimal subtotalComIvaEur
) {}