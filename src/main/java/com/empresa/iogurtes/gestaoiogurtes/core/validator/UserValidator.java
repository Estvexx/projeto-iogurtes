package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.users.*;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserValidator {
    public UserValidator() {

    }
    // FUNCIONÁRIOS
    public void validateCreateFuncionario(CreateFuncionarioRequest info) {
        validarPassword(info.password());
        validarDataAdmissao(info.dataAdmissao());
    }

    public void validateUpdateFuncionario(UpdateFuncionarioRequest info) {
        validarDataAdmissao(info.dataAdmissao());
        UserRoleType role = info.novaRole();
        if (info.turno() == null && (role == UserRoleType.FUNCIONARIO_MP
                || role == UserRoleType.FUNCIONARIO_OP
                || role == null )) throw new ValidationException(ValidationErrorCode.TURNO_REQUIRED);
    }

    // CLIENTES -> sem update porque o jakarta faz no DTO
    public void validateCreateCliente(CreateClienteRequest info) {
        validarPassword(info.password());
    }

    // ADMIN -> sem update porque o jakarta faz no DTO
    public void validateCreateAdmin(CreateAdminRequest info) {
        validarPassword(info.password());
    }

    // GESTOR

    public void validateCreateGestor(CreateGestorRequest info) {
        validarPassword(info.password());
        validarDataAdmissao(info.dataAdmissao());
    }



    public void validateUpdateGestor(UpdateGestorRequest info) {
        validarDataAdmissao(info.dataAdmissao());

        if (info.novaRole() == UserRoleType.FUNCIONARIO_MP
                || info.novaRole() == UserRoleType.FUNCIONARIO_OP)
            if (info.turno() == null)
                throw new ValidationException(ValidationErrorCode.TURNO_REQUIRED);
    }

    private void validarPassword(String password) {
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

}