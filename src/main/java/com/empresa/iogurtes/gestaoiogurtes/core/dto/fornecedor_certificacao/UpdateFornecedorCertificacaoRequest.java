package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateFornecedorCertificacaoRequest(
        @NotNull(message = "Data de início é obrigatória")
        LocalDate dataInicio,

        @NotNull(message = "Data de fim é obrigatória")
        LocalDate dataFim
) {}