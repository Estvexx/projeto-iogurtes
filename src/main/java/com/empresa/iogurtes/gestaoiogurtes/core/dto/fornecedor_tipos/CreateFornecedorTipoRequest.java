package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateFornecedorTipoRequest(
        @NotBlank(message = "Nome da certificação é obrigatório")
        @Size(min = 4, max = 80, message = "Nome deve ter entre 2 e 80 caracteres")
        String nome,

        @NotBlank(message = "Descrição é obrigatória")
        @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres")
        String descricao
) {}