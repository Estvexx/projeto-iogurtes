package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import java.time.LocalDate;

public record CreateGestorRequest(
        String nome,
        String email,
        String password,
        LocalDate dataAdmissao
) {}