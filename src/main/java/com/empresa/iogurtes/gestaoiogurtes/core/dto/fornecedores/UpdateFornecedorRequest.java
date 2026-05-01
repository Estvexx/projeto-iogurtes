package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores;

import java.util.UUID;

public record UpdateFornecedorRequest(
        String nome,
        String nif,
        String email,
        String telefone,
        String morada,
        String cidade,
        UUID tipoId
) {}