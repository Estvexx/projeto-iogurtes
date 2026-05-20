package com.empresa.iogurtes.gestaoiogurtes.core.dto.pallet_tipo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreatePalletTipoRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 80, message = "Nome deve ter entre 2 e 80 caracteres")
        String nome,

        @NotNull(message = "Capacidade é obrigatória")
        @DecimalMin(value = "0.001", message = "Capacidade deve ser maior que zero")
        @Digits(integer = 7, fraction = 3, message = "Capacidade inválida")
        BigDecimal capacidadeKg
) {}