package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao;

import java.time.LocalDate;

public record UpdateFornecedorCertificacaoRequest(
        LocalDate dataInicio,
        LocalDate dataFim
) {}