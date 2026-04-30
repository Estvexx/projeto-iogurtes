package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import java.time.LocalDate;

public record CreateFuncionarioRequest(
        String nome,
        String email,
        String password,
        String turno,
        LocalDate dataAdmissao
) {}