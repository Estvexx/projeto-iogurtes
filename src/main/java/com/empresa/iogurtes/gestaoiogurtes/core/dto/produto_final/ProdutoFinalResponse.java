package com.empresa.iogurtes.gestaoiogurtes.core.dto.produto_final;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoFisico;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProdutoFinalResponse(
        UUID id,
        String codigoSku,
        String nome,
        String descricao,
        String abreviacaoSabor,
        EstadoFisico estadoFisico,
        Integer validadeDias,
        BigDecimal precoVenda,
        BigDecimal precoPorKg,
        BigDecimal taxaIva,
        boolean visivelCliente,
        Integer quantidadeLote,
        List<ProdutoMateriaResponse> composicao,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}