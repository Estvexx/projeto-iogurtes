package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;

import java.time.LocalDate;

public record ValidatedUpdateFuncionario(
        String nome,
        TurnoTipo turno,
        LocalDate dataAdmissao,
        UserRoleType novaRole
) {}