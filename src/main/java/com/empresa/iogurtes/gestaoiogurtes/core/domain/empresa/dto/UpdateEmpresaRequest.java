package com.empresa.iogurtes.gestaoiogurtes.core.domain.empresa.dto;

public record UpdateEmpresaRequest(
        String nomeEmpresa,
        String nipc,
        String telefone,
        String morada,
        String codigoPostal,
        String cidade
) {}