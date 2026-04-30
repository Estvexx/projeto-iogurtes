package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

public record CreateAdminRequest(
        String nome,
        String email,
        String password
) {}