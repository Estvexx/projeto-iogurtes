package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao;
import java.time.LocalDate;

public record ValidatedUpdateFornecedorCertificacao(
        LocalDate dataInicio,
        LocalDate dataFim
) {}