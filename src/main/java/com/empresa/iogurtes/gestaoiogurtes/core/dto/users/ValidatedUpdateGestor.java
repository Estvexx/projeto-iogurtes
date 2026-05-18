package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;

import java.time.LocalDate;

public record ValidatedUpdateGestor(
        String nome,
        LocalDate dataAdmissao,
        TurnoTipo turno,
        UserRoleType novaRole
) {}