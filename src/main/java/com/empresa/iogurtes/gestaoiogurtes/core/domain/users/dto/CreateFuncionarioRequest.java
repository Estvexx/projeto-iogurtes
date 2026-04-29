package com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto;

import java.time.LocalDate;

public record CreateFuncionarioRequest(
        String nome,
        String email,
        String password,
        String turno,
        LocalDate dataAdmissao
) {}