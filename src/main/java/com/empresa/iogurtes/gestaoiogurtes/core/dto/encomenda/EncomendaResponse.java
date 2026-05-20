package com.empresa.iogurtes.gestaoiogurtes.core.dto.encomenda;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomenda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EncomendaResponse(
        UUID id,
        UUID userId,
        String userNome,
        UUID moedaId,
        String moedaCodigo,
        String moedaSimbolo,
        BigDecimal taxaConversaoSnapshot,
        EstadoEncomenda estado,
        LocalDateTime dataEncomenda,
        BigDecimal totalPreco,
        BigDecimal totalPrecoEur,
        List<EncomendaPalletResponse> pallets,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}