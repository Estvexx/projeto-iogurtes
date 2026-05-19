package com.empresa.iogurtes.gestaoiogurtes.core.dto.moeda;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateMoedaRequest(

        @NotBlank(message = "Código da moeda é obrigatório")
        @Size(min = 3, max = 3, message = "Código deve ter exatamente 3 caracteres")
        @Pattern(regexp = "^[A-Za-z]{3}$", message = "Código deve conter apenas letras (ex: EUR, USD)")
        String codigo,

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