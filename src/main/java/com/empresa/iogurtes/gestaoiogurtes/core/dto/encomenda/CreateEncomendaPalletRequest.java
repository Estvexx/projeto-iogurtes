package com.empresa.iogurtes.gestaoiogurtes.core.dto.encomenda;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateEncomendaPalletRequest(

        @NotNull(message = "Produto é obrigatório")
        UUID produtoId,

        @NotNull(message = "Tipo de pallet é obrigatório")
        UUID palletTipoId,

        @NotNull(message = "Quantidade de pallets é obrigatória")
        @Min(value = 1, message = "Quantidade de pallets deve ser pelo menos 1")
        Integer quantidadePallets
) {}