package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateGestorRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 4, max = 60, message = "Nome deve ter entre 4 e 60 caracteres")
        String nome,

        LocalDate dataAdmissao,

        String turno,

        UserRoleType novaRole
) {}