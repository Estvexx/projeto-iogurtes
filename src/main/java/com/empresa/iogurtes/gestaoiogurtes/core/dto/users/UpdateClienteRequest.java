package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import java.util.UUID;

public record UpdateClienteRequest(
        String nome,
        UUID empresaId
) {}