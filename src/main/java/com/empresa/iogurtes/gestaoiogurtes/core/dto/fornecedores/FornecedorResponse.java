package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.FornecedorCertificacaoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.FornecedorTipoResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record FornecedorResponse(
        UUID id,
        String nome,
        String nif,
        String email,
        String telefone,
        String morada,
        String cidade,
        FornecedorTipoResponse tipo,
        List<FornecedorCertificacaoResponse> certificacoes,
        boolean isActive,
        LocalDateTime createdAt
) {}