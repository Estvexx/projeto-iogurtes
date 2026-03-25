package com.empresa.iogurtes.gestaoiogurtes.core.validator;

public class LoginValidator {
    public void validate(String email, String password) {
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Email inválido");
        if (password == null || password.length() < 6)
            throw new IllegalArgumentException("Password inválida");
    }
}
