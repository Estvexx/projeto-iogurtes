package com.empresa.iogurtes.gestaoiogurtes.core.dto.ordemproducao;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoOrdem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrdemProducaoResponse(
        UUID id,
        UUID userId,
        String userNome,
        EstadoOrdem estado,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        LocalDateTime aprovadoEm,
        String observacoes,
        List<OrdemProducaoProdutoResponse> produtos,
        List<ConsumoProducaoResponse> consumos,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}