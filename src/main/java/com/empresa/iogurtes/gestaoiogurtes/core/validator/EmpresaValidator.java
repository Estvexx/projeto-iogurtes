package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.CreateEmpresaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.UpdateEmpresaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.ValidatedEmpresa;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.ValidatedUpdateEmpresa;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationException;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.EmpresaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.utils.PhoneUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmpresaValidator {

    private final EmpresaRepository empresaRepository;

    public EmpresaValidator(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public ValidatedEmpresa validateCreateEmpresa(CreateEmpresaRequest request) {
        validarNomeEmpresa(request.nomeEmpresa());
        validarNipc(request.nipc(), null);
        String telefone = validarTelefone(request.telefone());
        validarMorada(request.morada());
        validarCodigoPostal(request.codigoPostal());
        validarCidade(request.cidade());

        return new ValidatedEmpresa(
                request.nomeEmpresa(),
                request.nipc(),
                telefone,
                request.morada(),
                request.codigoPostal(),
                request.cidade()
        );
    }

    public ValidatedUpdateEmpresa validateUpdateEmpresa(UUID id, UpdateEmpresaRequest request) {
        validarNomeEmpresa(request.nomeEmpresa());
        validarNipc(request.nipc(), id);
        String telefone = validarTelefone(request.telefone());
        validarMorada(request.morada());
        validarCodigoPostal(request.codigoPostal());
        validarCidade(request.cidade());

        return new ValidatedUpdateEmpresa(
                request.nomeEmpresa(),
                request.nipc(),
                telefone,
                request.morada(),
                request.codigoPostal(),
                request.cidade()
        );
    }


    private void validarNomeEmpresa(String nomeEmpresa) {
        if (nomeEmpresa == null || nomeEmpresa.isBlank())
            throw new ValidationException(ValidationErrorCode.NOME_EMPRESA_NULL);
        if (nomeEmpresa.length() < 3)
            throw new ValidationException(ValidationErrorCode.NOME_EMPRESA_TOO_SHORT);
        if (nomeEmpresa.length() > 150)
            throw new ValidationException(ValidationErrorCode.NOME_EMPRESA_TOO_LONG);
    }

    private void validarNipc(String nipc, UUID id) {
        if (nipc == null || nipc.isBlank())
            throw new ValidationException(ValidationErrorCode.NIPC_NULL);
        if (!nipc.matches("^\\d{9}$"))
            throw new ValidationException(ValidationErrorCode.NIPC_INVALID);
        // NULL para create, !NULL para update
        if(id != null){
            if (empresaRepository.existsByNipcAndIdNot(nipc, id))
                throw new ValidationException(ValidationErrorCode.NIPC_ALREADY_EXISTS);
        } else {
            if (empresaRepository.existsByNipc(nipc))
                throw new ValidationException(ValidationErrorCode.NIPC_ALREADY_EXISTS);
        }
    }

    private String validarTelefone(String telefone) {
        return PhoneUtils.validarENormalizar(telefone);
    }

    private void validarMorada(String morada) {
        if (morada == null || morada.isBlank()) return;
        if (morada.length() > 200)
            throw new ValidationException(ValidationErrorCode.MORADA_TOO_LONG);
    }

    private void validarCodigoPostal(String codigoPostal) {
        if (codigoPostal == null || codigoPostal.isBlank()) return;
        if (!codigoPostal.matches("^\\d{4}-\\d{3}$"))
            throw new ValidationException(ValidationErrorCode.CODIGO_POSTAL_INVALID);
    }

    private void validarCidade(String cidade) {
        if (cidade == null || cidade.isBlank()) return;
        if (cidade.length() > 100)
            throw new ValidationException(ValidationErrorCode.CIDADE_TOO_LONG);
    }
}