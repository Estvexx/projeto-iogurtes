package com.empresa.iogurtes.gestaoiogurtes.core.dto.ordemproducao;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrdemProducaoProdutoRequest(

        @NotNull(message = "Produto é obrigatório")
        UUID produtoId,

        @NotNull(message = "Quantidade em kg é obrigatória")
        @DecimalMin(value = "0.001", message = "Quantidade deve ser maior que zero")
        @Digits(integer = 9, fraction = 3, message = "Quantidade inválida")
        BigDecimal quantidadeKg
) {}