package com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao;

import java.time.LocalDateTime;
import java.util.UUID;

public record CertificacaoResponse(
        UUID id,
        String nome,
        String descricao,
        boolean isActive,
        LocalDateTime createdAt
) {}