package com.empresa.iogurtes.gestaoiogurtes.core.dto.auth;


import java.util.UUID;

public record LoginResponse(
        UUID id,
        String nome,
        String email,
        String role,
        String token
) {
}