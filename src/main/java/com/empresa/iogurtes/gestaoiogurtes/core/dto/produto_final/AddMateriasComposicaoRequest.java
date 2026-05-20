package com.empresa.iogurtes.gestaoiogurtes.core.dto.produto_final;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AddMateriasComposicaoRequest(

        @NotEmpty(message = "Deve adicionar pelo menos uma matéria prima")
        @Valid
        List<CreateProdutoMateriaRequest> materias
) {}