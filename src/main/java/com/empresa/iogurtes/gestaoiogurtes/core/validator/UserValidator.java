package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UserValidator {

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateCreateUser(String nome,
                                   String email,
                                   String password,
                                   TurnoTipo turno,
                                   LocalDate dataAdmissao,
                                   List<UserRole> roles) {

        validarNome(nome);
        validarPassword(password);
        validarRoles(roles);
        validarEmail(email);
        validarDataAdmissao(dataAdmissao);
        validarTurnoPorRole(turno, roles);
    }

    private void validarNome(String nome) {
        if (nome == null || nome.length() < 4 || nome.length() > 60) {
            throw new IllegalArgumentException("Nome deve ter entre 4 e 60 caracteres");
        }
    }

    private void validarPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("A password deve ter pelo menos 8 caracteres");
        }

        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSymbol = password.chars().anyMatch(c -> "!@#$%^&*()_+-=[]{}|;:'\",.<>?/".indexOf(c) >= 0); // VERIFICAR SE SÓ ASSIM POR ACASO TEM SIMBOLOS

        if (!hasUpper) throw new IllegalArgumentException("A password deve ter pelo menos uma letra maiúscula");
        if (!hasLower) throw new IllegalArgumentException("A password deve ter pelo menos uma letra minúscula");
        if (!hasDigit) throw new IllegalArgumentException("A password deve ter pelo menos um número");
        if (!hasSymbol) throw new IllegalArgumentException("A password deve ter pelo menos um símbolo especial");
    }

    private void validarRoles(List<UserRole> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("O utilizador deve ter pelo menos uma role");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Já existe um utilizador com este email");
        }
    }

    private void validarDataAdmissao(LocalDate dataAdmissao) {
        if (dataAdmissao != null && dataAdmissao.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de admissão não pode ser no futuro");
        }
    }

    private void validarTurnoPorRole(TurnoTipo turno, List<UserRole> roles) {

        boolean precisaTurno = roles.stream()
                .anyMatch(role -> role.getRole().name().equals("FUNCIONARIO"));

        if (precisaTurno && turno == null) {
            throw new IllegalArgumentException("Funcionarios precisam de turno");
        }
    }
}
