package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.CreateCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.UpdateCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.ValidatedCertificacao;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.ValidatedUpdateCertificacao;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationException;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.CertificacaoRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CertificacaoValidator {

    private final CertificacaoRepository certificacaoRepository;

    public CertificacaoValidator(CertificacaoRepository certificacaoRepository) {
        this.certificacaoRepository = certificacaoRepository;
    }

    public ValidatedCertificacao validateCreateCertificacao(CreateCertificacaoRequest info) {
        validarNome(info.nome());
        validarDescricao(info.descricao());
        if (certificacaoRepository.existsByNome(info.nome()))
            throw new ValidationException(ValidationErrorCode.NOME_CERTIFICACAO_ALREADY_EXISTS);

        return new ValidatedCertificacao(info.nome(), info.descricao());
    }

    public ValidatedUpdateCertificacao validateUpdateCertificacao(UUID id, UpdateCertificacaoRequest info) {
        validarNome(info.nome());
        validarDescricao(info.descricao());
        if (certificacaoRepository.existsByNomeAndIdNot(info.nome(), id))
            throw new ValidationException(ValidationErrorCode.NOME_CERTIFICACAO_ALREADY_EXISTS_UPDATE);

        return new ValidatedUpdateCertificacao(info.nome(), info.descricao());
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank())
            throw new ValidationException(ValidationErrorCode.NOME_CERTIFICACAO_NULL);
        if (nome.length() < 2)
            throw new ValidationException(ValidationErrorCode.NOME_CERTIFICACAO_TOO_SHORT);
        if (nome.length() > 80)
            throw new ValidationException(ValidationErrorCode.NOME_CERTIFICACAO_TOO_LONG);
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