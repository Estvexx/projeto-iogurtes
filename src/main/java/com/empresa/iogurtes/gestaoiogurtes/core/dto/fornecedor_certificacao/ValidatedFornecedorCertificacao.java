package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Certificacao;

import java.time.LocalDate;

public record ValidatedFornecedorCertificacao(
        Certificacao certificacao,
        LocalDate dataInicio,
        LocalDate dataFim
) {}
