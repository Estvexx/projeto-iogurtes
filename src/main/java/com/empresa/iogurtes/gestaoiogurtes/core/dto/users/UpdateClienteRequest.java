package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateClienteRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 4, max = 60, message = "Nome deve ter entre 4 e 60 caracteres")
        String nome,

        UUID empresaId
) {}