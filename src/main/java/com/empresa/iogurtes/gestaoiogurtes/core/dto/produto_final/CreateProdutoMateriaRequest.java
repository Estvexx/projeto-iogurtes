package com.empresa.iogurtes.gestaoiogurtes.core.dto.produto_final;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProdutoMateriaRequest(

        @NotNull(message = "Matéria prima é obrigatória")
        UUID materiaId,

        @NotNull(message = "Quantidade por unidade de produto é obrigatória")
        @DecimalMin(value = "0.001", message = "Quantidade deve ser maior que zero")
        @Digits(integer = 9, fraction = 3, message = "Quantidade inválida")
        BigDecimal quantidadePorUnidadeProduto
) {}
