package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;

import java.time.LocalDate;

public record UpdateFuncionarioRequest(
        String nome,
        String turno,
        LocalDate dataAdmissao,
        UserRoleType novaRole
) {}