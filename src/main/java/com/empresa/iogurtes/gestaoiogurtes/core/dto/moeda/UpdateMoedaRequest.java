package com.empresa.iogurtes.gestaoiogurtes.core.dto.moeda;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateMoedaRequest(

        @NotBlank(message = "Nome da moeda é obrigatório")
        @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
        String nome,

        @NotBlank(message = "Símbolo da moeda é obrigatório")
        @Size(min = 1, max = 5, message = "Símbolo deve ter entre 1 e 5 caracteres")
        String simbolo,

        @NotNull(message = "Taxa de conversão EUR é obrigatória")
        @DecimalMin(value = "0.000001", message = "Taxa de conversão deve ser maior que zero")
        @Digits(integer = 6, fraction = 6, message = "Taxa de conversão inválida (máximo 6 casas decimais)")
        BigDecimal taxaConversaoEur
) {}
