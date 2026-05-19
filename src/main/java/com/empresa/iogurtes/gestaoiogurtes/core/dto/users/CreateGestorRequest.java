package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateGestorRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 4, max = 60, message = "Nome deve ter entre 4 e 60 caracteres")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "Password é obrigatória")
        @Size(min = 8, message = "Password deve ter no mínimo 8 caracteres")
        String password,

        LocalDate dataAdmissao
) {}