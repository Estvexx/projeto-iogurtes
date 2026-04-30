package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;

import java.time.LocalDate;

public record ValidatedGestor(
        String nome,
        String email,
        String password,
        UserRole role,
        LocalDate dataAdmissao
) {}