package com.empresa.iogurtes.gestaoiogurtes.core.dto.materiaprima;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MateriaFornecedorResponse(
        UUID id,
        UUID materiaId,
        String materiaNome,
        UUID fornecedorId,
        String fornecedorNome,
        UUID moedaId,
        String moedaCodigo,
        String moedaSimbolo,
        BigDecimal precoUnitario,
        BigDecimal precoUnitarioEur,
        Integer prazoEstimadoEntregaDias,
        boolean preferencial,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
