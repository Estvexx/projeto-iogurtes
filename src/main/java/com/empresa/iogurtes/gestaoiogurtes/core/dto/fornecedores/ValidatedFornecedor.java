package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.ValidatedFornecedorCertificacao;
import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorTipo;

import java.util.List;

public record ValidatedFornecedor(
        String nome,
        String nif,
        String email,
        String telefone,
        String morada,
        String cidade,
        FornecedorTipo tipo,
        List<ValidatedFornecedorCertificacao> certificacoes
) {}