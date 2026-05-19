package com.empresa.iogurtes.gestaoiogurtes.core.dto.materiaprima;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.materias_tipo.MateriaTipoResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MateriaPrimaResponse(
        UUID id,
        String nome,
        String unidade,
        BigDecimal stockAtual,
        BigDecimal stockMinimo,
        BigDecimal taxaIva,
        MateriaTipoResponse tipo,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}