package com.empresa.iogurtes.gestaoiogurtes.core.dto.users;

import java.util.UUID;

public record ValidatedUpdateCliente(
        String nome,
        UUID empresaId
) {}