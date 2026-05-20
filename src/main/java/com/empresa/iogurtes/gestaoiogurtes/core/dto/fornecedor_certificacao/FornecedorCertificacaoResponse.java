package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao;

import java.time.LocalDate;
import java.util.UUID;

public record FornecedorCertificacaoResponse(
        UUID id,
        String nomeFornecedor,
        String certificacaoNome,
        LocalDate dataInicio,
        LocalDate dataFim,
        boolean isActive
) {}