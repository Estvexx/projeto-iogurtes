package com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Empresa;
import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;

public record ValidatedCliente(
        String nome,
        String email,
        String password,
        UserRole role,
        Empresa empresa
) {}