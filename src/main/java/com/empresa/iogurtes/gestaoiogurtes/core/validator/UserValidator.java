package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto.*;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Empresa;
import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.EmpresaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRoleRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserValidator {

    private final UserRepository userRepository;
    private final EmpresaRepository empresaRepository;
    private final UserRoleRepository userRoleRepository;

    public UserValidator(UserRepository userRepository,
                         EmpresaRepository empresaRepository,
                         UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.empresaRepository = empresaRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public ValidatedFuncionario validateCreateFuncionario(CreateFuncionarioRequest info) {
        validarNome(info.nome());
        validarEmail(info.email());
        validarPassword(info.password());
        validarDataAdmissao(info.dataAdmissao());

        TurnoTipo turno = parseTurno(info.turno());
        if (turno == null) throw new ValidationException(ValidationErrorCode.TURNO_REQUIRED);

        UserRole role = parseRole(info.role());

        return new ValidatedFuncionario(info.nome(), info.email(), info.password(),
                turno, role, info.dataAdmissao());
    }

    public ValidatedCliente validateCreateCliente(CreateClienteRequest info) {
        validarNome(info.nome());
        validarEmail(info.email());
        validarPassword(info.password());

        if (info.empresaId() == null) throw new ValidationException(ValidationErrorCode.EMPRESA_NULL);
        Empresa empresa = empresaRepository.findById(info.empresaId())
                .orElseThrow(() -> new ValidationException(ValidationErrorCode.EMPRESA_NOT_FOUND));

        UserRole role = parseRole(info.role());

        return new ValidatedCliente(info.nome(), info.email(), info.password(), role, empresa);
    }

    public ValidatedAdmin validateCreateAdmin(CreateAdminRequest info) {
        validarNome(info.nome());
        validarEmail(info.email());
        validarPassword(info.password());

        UserRole role = parseRole(info.role());

        return new ValidatedAdmin(info.nome(), info.email(), info.password(), role);
    }

    public ValidatedGestor validateCreateGestor(CreateGestorRequest info) {
        validarNome(info.nome());
        validarEmail(info.email());
        validarPassword(info.password());
        validarDataAdmissao(info.dataAdmissao());

        UserRole role = parseRole(info.role());

        return new ValidatedGestor(info.nome(), info.email(), info.password(),
                role, info.dataAdmissao());
    }

    public ValidatedUpdateFuncionario validateUpdateFuncionario(UpdateFuncionarioRequest info) {
        validarNome(info.nome());
        validarDataAdmissao(info.dataAdmissao());

        TurnoTipo turno = parseTurno(info.turno());
        if (turno == null) throw new ValidationException(ValidationErrorCode.TURNO_REQUIRED);

        return new ValidatedUpdateFuncionario(info.nome(), turno, info.dataAdmissao());
    }

    public ValidatedUpdateCliente validateUpdateCliente(UpdateClienteRequest info) {
        validarNome(info.nome());
        return new ValidatedUpdateCliente(info.nome());
    }

    public ValidatedUpdateAdmin validateUpdateAdmin(UpdateAdminRequest info) {
        validarNome(info.nome());
        return new ValidatedUpdateAdmin(info.nome());
    }

    public ValidatedUpdateGestor validateUpdateGestor(UpdateGestorRequest info) {
        validarNome(info.nome());
        validarDataAdmissao(info.dataAdmissao());
        return new ValidatedUpdateGestor(info.nome(), info.dataAdmissao());
    }

    private void validarNome(String nome) {
        if (nome == null) throw new ValidationException(ValidationErrorCode.NOME_NULL);
        if (nome.length() < 4) throw new ValidationException(ValidationErrorCode.NOME_TOO_SHORT);
        if (nome.length() > 60) throw new ValidationException(ValidationErrorCode.NOME_TOO_LONG);
    }

    private void validarEmail(String email) {
        if (email == null || email.isBlank()) throw new ValidationException(ValidationErrorCode.EMAIL_NULL);
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) throw new ValidationException(ValidationErrorCode.EMAIL_INVALID_FORMAT);
        if (userRepository.existsByEmail(email)) throw new ValidationException(ValidationErrorCode.EMAIL_ALREADY_EXISTS);
    }

    private void validarPassword(String password) {
        if (password == null) throw new ValidationException(ValidationErrorCode.PASSWORD_NULL);
        if (password.length() < 8) throw new ValidationException(ValidationErrorCode.PASSWORD_TOO_SHORT);

        if (password.chars().noneMatch(Character::isUpperCase))
            throw new ValidationException(ValidationErrorCode.PASSWORD_NO_UPPERCASE);
        if (password.chars().noneMatch(Character::isLowerCase))
            throw new ValidationException(ValidationErrorCode.PASSWORD_NO_LOWERCASE);
        if (password.chars().noneMatch(Character::isDigit))
            throw new ValidationException(ValidationErrorCode.PASSWORD_NO_DIGIT);
        if (password.chars().noneMatch(c -> "!@#$%^&*()_+-=[]{}|;:'\",.<>?/".indexOf(c) >= 0))
            throw new ValidationException(ValidationErrorCode.PASSWORD_NO_SYMBOL);
    }

    private void validarDataAdmissao(LocalDate dataAdmissao) {
        if (dataAdmissao != null && dataAdmissao.isAfter(LocalDate.now()))
            throw new ValidationException(ValidationErrorCode.DATA_ADMISSAO_FUTURE);
    }

    private TurnoTipo parseTurno(String turno) {
        if (turno == null) return null; //retorno null porque para as outras validaçoes
        try {
            return TurnoTipo.valueOf(turno.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException(ValidationErrorCode.TURNO_INVALID);
        }
    }

    private UserRole parseRole(String role) {
        if (role == null) throw new ValidationException(ValidationErrorCode.ROLE_NULL);
        try {
            UserRoleType roleType = UserRoleType.valueOf(role.toUpperCase());
            return userRoleRepository.findByRole(roleType)
                    .orElseThrow(() -> new ValidationException(ValidationErrorCode.ROLE_NOT_FOUND));
        } catch (IllegalArgumentException e) {
            throw new ValidationException(ValidationErrorCode.ROLE_INVALID);
        }
    }
}