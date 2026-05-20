package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AddCertificacaoRequest(
        @NotNull(message = "ID da certificação é obrigatório")
        UUID certificacaoId,

        @NotNull(message = "Data de início é obrigatória")
        LocalDate dataInicio,

        @NotNull(message = "Data de fim é obrigatória")
        LocalDate dataFim
) {}