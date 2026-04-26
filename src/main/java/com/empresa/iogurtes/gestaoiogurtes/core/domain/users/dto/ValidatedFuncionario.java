package com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto;

import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;

import java.time.LocalDate;

public record ValidatedFuncionario(
        String nome,
        String email,
        String password,
        TurnoTipo turno,
        UserRole role,
        LocalDate dataAdmissao
) {}