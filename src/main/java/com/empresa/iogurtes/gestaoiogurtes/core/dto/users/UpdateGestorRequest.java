package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;

import java.time.LocalDate;

public record UpdateGestorRequest(
        String nome,
        LocalDate dataAdmissao,
        String turno,
        UserRoleType novaRole
) {}