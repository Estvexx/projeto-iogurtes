package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import java.util.UUID;

public record CreateClienteRequest(
        String nome,
        String email,
        String password,
        UUID empresaId
) {}