package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.AddCertificacaoRequest;

import java.util.List;
import java.util.UUID;

public record CreateFornecedorRequest(
        String nome,
        String nif,
        String email,
        String telefone,
        String morada,
        String cidade,
        UUID tipoId,
        List<AddCertificacaoRequest> certificacoes
) {}
