package com.empresa.iogurtes.gestaoiogurtes.core.dto.materiaprima;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record UpdateMateriaPrimaRequest(

        @NotBlank(message = "Nome da matéria-prima é obrigatório")
        @Size(min = 2, max = 120, message = "Nome deve ter entre 2 e 120 caracteres")
        String nome,

        @Size(max = 10, message = "Unidade deve ter no máximo 10 caracteres")
        String unidade,

        @NotNull(message = "Stock mínimo é obrigatório")
        @DecimalMin(value = "0.0", message = "Stock mínimo não pode ser negativo")
        @Digits(integer = 9, fraction = 3, message = "Stock mínimo inválido")
        BigDecimal stockMinimo,

        @NotNull(message = "Taxa de IVA é obrigatória")
        @DecimalMin(value = "0.0", message = "Taxa de IVA não pode ser negativa")
        @Digits(integer = 3, fraction = 2, message = "Taxa de IVA inválida")
        BigDecimal taxaIva,

        @NotNull(message = "Tipo é obrigatório")
        UUID tipoId
) {}