package com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto;

import java.time.LocalDate;

public record ValidatedUpdateGestor(
        String nome,
        LocalDate dataAdmissao
) {}