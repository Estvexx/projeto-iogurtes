package com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto;

import java.time.LocalDate;

public record CreateGestorRequest(
        String nome,
        String email,
        String password,
        LocalDate dataAdmissao
) {}