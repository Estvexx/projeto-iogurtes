package com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;

import java.time.LocalDate;

public record ValidatedUpdateFuncionario(
        String nome,
        TurnoTipo turno,
        LocalDate dataAdmissao
) {}