package com.empresa.iogurtes.gestaoiogurtes.core.dto.encomendamp;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomendaMP;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EncomendaMPResponse(
        UUID id,
        UUID userId,
        String userNome,
        UUID fornecedorId,
        String fornecedorNome,
        UUID moedaId,
        String moedaCodigo,
        String moedaSimbolo,
        BigDecimal taxaConversaoSnapshot,
        EstadoEncomendaMP estado,
        LocalDateTime dataEncomenda,
        LocalDate dataEntregaPrevista,
        BigDecimal totalPrecoSemIva,
        BigDecimal totalPrecoEurSemIva,
        BigDecimal totalPrecoEurComIva,
        String observacoes,
        List<EncomendaMPLinhaResponse> linhas,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}