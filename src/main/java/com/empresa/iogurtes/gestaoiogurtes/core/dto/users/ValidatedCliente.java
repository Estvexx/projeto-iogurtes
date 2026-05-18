package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Empresa;
import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;

public record ValidatedCliente(
        String nome,
        String email,
        String password,
        UserRole role,
        Empresa empresa
) {}