package com.empresa.iogurtes.gestaoiogurtes.core.dto.encomendamp;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

public record CreateEncomendaMPRequest(

        @NotNull(message = "Utilizador é obrigatório")
        UUID userId,

        @NotNull(message = "Fornecedor é obrigatório")
        UUID fornecedorId,

        @NotNull(message = "Moeda é obrigatória")
        UUID moedaId,

        @Size(max = 200, message = "Observações não podem exceder 200 caracteres")
        String observacoes,

        @NotEmpty(message = "Encomenda deve ter pelo menos uma linha")
        @Valid
        List<CreateEncomendaMPLinhaRequest> linhas
) {}
