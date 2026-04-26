package com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto;

import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;

import java.time.LocalDate;

public record ValidatedGestor(
        String nome,
        String email,
        String password,
        UserRole role,
        LocalDate dataAdmissao
) {}