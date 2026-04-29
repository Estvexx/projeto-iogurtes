package com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto;

import java.util.UUID;

public record CreateClienteRequest(
        String nome,
        String email,
        String password,
        UUID empresaId
) {}