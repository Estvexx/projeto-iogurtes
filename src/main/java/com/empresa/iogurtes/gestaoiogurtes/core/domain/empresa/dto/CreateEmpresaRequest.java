package com.empresa.iogurtes.gestaoiogurtes.core.domain.empresa.dto;

public record CreateEmpresaRequest(
        String nomeEmpresa,
        String nipc,
        String telefone,
        String morada,
        String codigoPostal,
        String cidade
) {}