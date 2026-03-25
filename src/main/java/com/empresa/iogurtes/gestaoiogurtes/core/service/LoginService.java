package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.ports.PasswordHasher;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;

public class LoginService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public LoginService(UserRepository userRepository,
                        PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public User execute(String email, String password) {
        if (email == null || !email.contains("@") || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Credenciais invalidas");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Credenciais invalidas"));

        if (!user.isActive()) {
            throw new IllegalArgumentException("Credenciais invalidas");
        }

        if (!passwordHasher.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciais invalidas");
        }

        return user;
    }
}