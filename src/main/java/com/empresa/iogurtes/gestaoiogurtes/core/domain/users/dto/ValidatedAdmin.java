package com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto;

import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;

public record ValidatedAdmin(
        String nome,
        String email,
        String password,
        UserRole role
) {}