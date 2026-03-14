package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.UserValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserValidator userValidator, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String nome,
                           String email,
                           String password,
                           TurnoTipo turno,
                           LocalDate dataAdmissao,
                           List<UserRole> roles) {

        userValidator.validateCreateUser(nome, email,password, turno, dataAdmissao, roles);

        String passwordHash;
        try {
            passwordHash = passwordEncoder.encode(password);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao encriptar a password", e);
        }

        User user = new User(
                null,
                nome,
                email,
                passwordHash,
                turno,
                dataAdmissao
        );

        for (UserRole role : roles) {
            role.setUser(user);
        }

        user.setRoles(roles);

        return userRepository.save(user);
    }
}