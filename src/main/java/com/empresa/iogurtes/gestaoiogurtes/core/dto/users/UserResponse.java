package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String nome,
        String email,
        TurnoTipo turno,
        UUID empresaId,
        LocalDate dataAdmissao,
        UserRoleType role,
        LocalDateTime createdAt
) {}