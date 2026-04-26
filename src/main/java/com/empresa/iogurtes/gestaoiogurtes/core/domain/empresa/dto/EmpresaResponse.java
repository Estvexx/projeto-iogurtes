
package com.empresa.iogurtes.gestaoiogurtes.core.domain.empresa.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EmpresaResponse(
        UUID id,
        String nomeEmpresa,
        String nipc,
        String telefone,
        String morada,
        String codigoPostal,
        String cidade,
        LocalDateTime createdAt
) {}