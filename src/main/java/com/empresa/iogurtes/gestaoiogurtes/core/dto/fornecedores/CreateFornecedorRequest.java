package com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.AddCertificacaoRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreateFornecedorRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 150, message = "Nome deve ter entre 2 e 150 caracteres")
        String nome,

        @NotBlank(message = "NIF é obrigatório")
        @Pattern(regexp = "^\\d{9}$", message = "NIF deve ter 9 dígitos")
        String nif,

        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @Size(max = 200, message = "Morada deve ter no máximo 200 caracteres")
        String morada,

        @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
        String cidade,

        UUID tipoId,

        List<AddCertificacaoRequest> certificacoes
) {}