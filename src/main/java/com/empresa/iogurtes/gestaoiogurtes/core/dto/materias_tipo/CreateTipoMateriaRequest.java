package com.empresa.iogurtes.gestaoiogurtes.core.dto.materias_tipo;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateTipoMateriaRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 80, message = "Nome deve ter entre 2 e 80 caracteres")
        String nome,

        @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
        String descricao,

        @NotNull(message = "Taxa de IVA é obrigatória")
        @DecimalMin(value = "0.0", message = "Taxa de IVA não pode ser negativa")
        @Digits(integer = 3, fraction = 2, message = "Taxa de IVA inválida")
        BigDecimal taxaIva
) {}