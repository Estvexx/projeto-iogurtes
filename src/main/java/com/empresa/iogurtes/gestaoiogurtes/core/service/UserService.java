package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Empresa;
import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;

import com.empresa.iogurtes.gestaoiogurtes.core.repository.EmpresaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.UserValidator;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Strings;

import java.time.LocalDate;
import java.util.List;

import java.util.UUID;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmpresaRepository empresaRepository;

    public UserService(UserRepository userRepository, UserValidator userValidator, BCryptPasswordEncoder passwordEncoder, EmpresaRepository empresaRepository) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public User createUser(String nome,
                           String email,
                           String password,
                           String turno,
                           LocalDate dataAdmissao,
                           List<String> roles,
                           UUID empresaId
                            ) {

        TurnoTipo turnoTipo = userValidator.validateAndParseTurno(turno);
        List<UserRole> userRoles = userValidator.validateAndParseRoles(roles);

        userValidator.validateCreateUser(nome, email,password, turnoTipo, dataAdmissao, userRoles, empresaId);

        String passwordHash = passwordEncoder.encode(password);
        // o getReferenceById é só para passar como objeto empresa o uuid, assim nao retorno o objeto inteiro, evito queries
        Empresa empresa = empresaId != null ? empresaRepository.getReferenceById(empresaId) : null;

        User user = new User(
                empresa,
                nome,
                email,
                passwordHash,
                turnoTipo,
                dataAdmissao
        );

        for (UserRole role : userRoles) {
            role.setUser(user);
        }

        user.setRoles(userRoles);

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(UUID id, String nome, String turno, List<String> roles) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado"));

        TurnoTipo turnoTipo = userValidator.validateAndParseTurno(turno);
        List<UserRole> userRoles = userValidator.validateAndParseRoles(roles);
        userValidator.validateUpdateUser(nome, turnoTipo, userRoles);

        // limpa as roles antigas e associa as novas ao user
        user.getRoles().clear();
        for (UserRole role : userRoles) {
            role.setUser(user);
        }

        user.setNome(nome);
        user.setTurno(turnoTipo);
        user.setRoles(userRoles);

        return userRepository.save(user);
    }

    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void delete(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado"));

        userRepository.delete(user);
    }
}