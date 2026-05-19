package com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateEmpresaRequest(
        @NotBlank(message = "Nome da empresa é obrigatório")
        @Size(min = 5, max = 150, message = "Nome deve ter entre 5 e 150 caracteres")
        String nomeEmpresa,

        @NotBlank(message = "NIPC é obrigatório")
        @Pattern(regexp = "^\\d{9}$", message = "NIPC deve ter 9 dígitos")
        String nipc,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @Size(max = 200, message = "Morada deve ter no máximo 200 caracteres")
        String morada,

        @Pattern(regexp = "^\\d{4}-\\d{3}$", message = "Código postal inválido (formato: 0000-000)")
        String codigoPostal,

        @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
        String cidade
) {}