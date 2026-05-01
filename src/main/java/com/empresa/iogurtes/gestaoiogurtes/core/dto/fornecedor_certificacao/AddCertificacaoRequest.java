package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao;

import java.time.LocalDate;
import java.util.UUID;

public record AddCertificacaoRequest(
        UUID certificacaoId,
        LocalDate dataInicio,
        LocalDate dataFim
) {}