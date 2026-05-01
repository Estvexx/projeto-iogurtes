package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores;

import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorTipo;

public record ValidatedUpdateFornecedor(
        String nome,
        String email,
        String telefone,
        String morada,
        String cidade,
        FornecedorTipo tipo
) {}