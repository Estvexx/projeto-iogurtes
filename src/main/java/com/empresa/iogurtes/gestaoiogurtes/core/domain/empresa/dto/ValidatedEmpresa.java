package com.empresa.iogurtes.gestaoiogurtes.core.domain.empresa.dto;

public record ValidatedEmpresa(
        String nomeEmpresa,
        String nipc,
        String telefone,
        String morada,
        String codigoPostal,
        String cidade
) {}