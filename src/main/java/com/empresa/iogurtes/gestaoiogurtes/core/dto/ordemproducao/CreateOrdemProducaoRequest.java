package com.empresa.iogurtes.gestaoiogurtes.core.dto.ordemproducao;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreateOrdemProducaoRequest(

        @NotNull(message = "Utilizador é obrigatório")
        UUID userId,

        @Size(max = 500, message = "Observações não podem exceder 500 caracteres")
        String observacoes,

        @NotEmpty(message = "Ordem de produção deve ter pelo menos um produto")
        @Valid
        List<CreateOrdemProducaoProdutoRequest> produtos
) {}