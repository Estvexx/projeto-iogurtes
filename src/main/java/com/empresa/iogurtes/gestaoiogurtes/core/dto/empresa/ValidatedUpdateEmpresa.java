package com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa;

public record ValidatedUpdateEmpresa(
        String nomeEmpresa,
        String nipc,
        String telefone,
        String morada,
        String codigoPostal,
        String cidade
) {}