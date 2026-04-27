package com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto;

import java.time.LocalDate;

public record UpdateFuncionarioRequest(
        String nome,
        String turno,
        LocalDate dataAdmissao
) {}