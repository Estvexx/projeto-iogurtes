package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;

public record ValidatedAdmin(
        String nome,
        String email,
        String password,
        UserRole role
) {}