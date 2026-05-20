package com.empresa.iogurtes.gestaoiogurtes.core.dto.materiaprima;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateMateriaFornecedorRequest(

        @NotNull(message = "Fornecedor é obrigatório")
        UUID fornecedorId,

        @NotNull(message = "Moeda é obrigatória")
        UUID moedaId,

        @NotNull(message = "Preço unitário é obrigatório")
        @DecimalMin(value = "0.01", message = "Preço unitário deve ser maior que zero")
        @Digits(integer = 8, fraction = 2, message = "Preço unitário inválido")
        BigDecimal precoUnitario,

        @Min(value = 1, message = "Prazo de entrega deve ser pelo menos 1 dia")
        Integer prazoEstimadoEntregaDias,

        @NotNull(message = "Preferencial é obrigatório")
        boolean preferencial
) {}