package com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto;

public record CreateAdminRequest(
        String nome,
        String email,
        String password
) {}