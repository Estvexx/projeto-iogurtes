package com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao;

import jakarta.validation.constraints.Size;

public record UpdateCertificacaoRequest(
        @Size(min = 2, max = 80, message = "Nome deve ter entre 2 e 80 caracteres")
        String nome,
        @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres")
        String descricao
) {}