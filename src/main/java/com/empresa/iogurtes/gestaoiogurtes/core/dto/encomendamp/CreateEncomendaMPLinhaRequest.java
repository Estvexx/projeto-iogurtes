package com.empresa.iogurtes.gestaoiogurtes.core.dto.encomendamp;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateEncomendaMPLinhaRequest(
        @NotNull(message = "Matéria prima é obrigatória")
        UUID materiaId,

        @NotNull(message = "Quantidade é obrigatória")
        @DecimalMin(value = "0.001", message = "Quantidade deve ser maior que zero")
        @Digits(integer = 9, fraction = 3, message = "Quantidade inválida")
        BigDecimal quantidade
) {}
