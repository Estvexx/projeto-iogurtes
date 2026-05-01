// FornecedorTipoValidator.java
package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.CreateFornecedorTipoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.UpdateFornecedorTipoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.ValidatedFornecedorTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.ValidatedUpdateFornecedorTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationException;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorTipoRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FornecedorTipoValidator {

    private final FornecedorTipoRepository fornecedorTipoRepository;

    public FornecedorTipoValidator(FornecedorTipoRepository fornecedorTipoRepository) {
        this.fornecedorTipoRepository = fornecedorTipoRepository;
    }

    public ValidatedFornecedorTipo validateCreateFornecedorTipo(CreateFornecedorTipoRequest info) {
        validarNome(info.nome());
        validarDescricao(info.descricao());
        if (fornecedorTipoRepository.existsByNome(info.nome()))
            throw new ValidationException(ValidationErrorCode.NOME_FORNECEDOR_TIPO_ALREADY_EXISTS);

        return new ValidatedFornecedorTipo(info.nome(), info.descricao());
    }

    public ValidatedUpdateFornecedorTipo validateUpdateFornecedorTipo(UUID id, UpdateFornecedorTipoRequest info) {
        validarNome(info.nome());
        validarDescricao(info.descricao());
        if (fornecedorTipoRepository.existsByNomeAndIdNot(info.nome(), id))
            throw new ValidationException(ValidationErrorCode.NOME_FORNECEDOR_TIPO_ALREADY_EXISTS_UPDATE);

        return new ValidatedUpdateFornecedorTipo(info.nome(), info.descricao());
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank())
            throw new ValidationException(ValidationErrorCode.NOME_FORNECEDOR_TIPO_NULL);
        if (nome.length() < 2)
            throw new ValidationException(ValidationErrorCode.NOME_FORNECEDOR_TIPO_TOO_SHORT);
        if (nome.length() > 80)
            throw new ValidationException(ValidationErrorCode.NOME_FORNECEDOR_TIPO_TOO_LONG);
    }

    private void validarDescricao(String descricao) {
        if (descricao == null || descricao.isBlank())
            throw new ValidationException(ValidationErrorCode.DESCRICAO_CERTIFICACAO_NULL);
        if (descricao.length() < 10)
            throw new ValidationException(ValidationErrorCode.DESCRICAO_CERTIFICACAO_TOO_SHORT);
        if (descricao.length() > 500)
            throw new ValidationException(ValidationErrorCode.DESCRICAO_CERTIFICACAO_TOO_LONG);
    }
}