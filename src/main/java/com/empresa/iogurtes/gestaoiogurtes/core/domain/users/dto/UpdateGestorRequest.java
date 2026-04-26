package com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto;

import java.time.LocalDate;

public record UpdateGestorRequest(
        String nome,
        LocalDate dataAdmissao
) {}