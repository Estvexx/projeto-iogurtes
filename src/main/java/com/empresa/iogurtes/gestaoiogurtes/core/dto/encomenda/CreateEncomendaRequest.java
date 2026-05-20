package com.empresa.iogurtes.gestaoiogurtes.core.dto.encomenda;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateEncomendaRequest(

        @NotNull(message = "Utilizador é obrigatório")
        UUID userId,

        @NotNull(message = "Moeda é obrigatória")
        UUID moedaId,

        @NotEmpty(message = "Encomenda deve ter pelo menos uma pallet")
        @Valid
        List<CreateEncomendaPalletRequest> pallets
) {}