// FornecedorValidator.java
package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.AddCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.UpdateFornecedorCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.ValidatedFornecedorCertificacao;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.ValidatedUpdateFornecedorCertificacao;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores.CreateFornecedorRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores.UpdateFornecedorRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores.ValidatedFornecedor;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores.ValidatedUpdateFornecedor;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Certificacao;
import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.CertificacaoRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorCertificacaoRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorTipoRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.utils.PhoneUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FornecedorValidator {

    private final FornecedorRepository fornecedorRepository;
    private final FornecedorTipoRepository fornecedorTipoRepository;
    private final CertificacaoRepository certificacaoRepository;
    private final FornecedorCertificacaoRepository fornecedorCertificacaoRepository;

    public FornecedorValidator(FornecedorRepository fornecedorRepository,
                               FornecedorTipoRepository fornecedorTipoRepository,
                               CertificacaoRepository certificacaoRepository,
                               FornecedorCertificacaoRepository fornecedorCertificacaoRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorTipoRepository = fornecedorTipoRepository;
        this.certificacaoRepository = certificacaoRepository;
        this.fornecedorCertificacaoRepository = fornecedorCertificacaoRepository;
    }

    public ValidatedFornecedor validateCreateFornecedor(CreateFornecedorRequest request) {
        validarNome(request.nome());
        validarNif(request.nif());
        validarEmailFornecedor(request.email());
        String telefone = validarTelefone(request.telefone());
        validarMorada(request.morada());
        validarCidade(request.cidade());

        FornecedorTipo tipo = parseTipo(request.tipoId());
        List<ValidatedFornecedorCertificacao> certificacoes = parseCertificacoes(request.certificacoes());

        return new ValidatedFornecedor(request.nome(), request.nif(), request.email(),
                telefone, request.morada(), request.cidade(), tipo, certificacoes);
    }


    public ValidatedUpdateFornecedor validateUpdateFornecedor(UUID id, UpdateFornecedorRequest request) {
        validarNome(request.nome());
        validarNifUpdate(id, request.nif());
        validarEmailFornecedor(request.email());
        String telefone = validarTelefone(request.telefone());
        validarMorada(request.morada());
        validarCidade(request.cidade());

        FornecedorTipo tipo = parseTipo(request.tipoId());

        return new ValidatedUpdateFornecedor(request.nome(), request.email(),
                telefone, request.morada(), request.cidade(), tipo);
    }

    // ─── Certificacao ────────────────────────────────────────────────────────────

    public ValidatedFornecedorCertificacao validateAddCertificacao(UUID fornecedorId,
                                                                   AddCertificacaoRequest request) {
        if (fornecedorCertificacaoRepository.existsByFornecedor_IdAndCertificacao_Id(
                fornecedorId, request.certificacaoId()))
            throw new ValidationException(ValidationErrorCode.NOME_CERTIFICACAO_ALREADY_EXISTS);

        Certificacao certificacao = certificacaoRepository.findById(request.certificacaoId())
                .orElseThrow(() -> new ValidationException(ValidationErrorCode.EMPRESA_NOT_FOUND));

        validarDatas(request.dataInicio(), request.dataFim());

        return new ValidatedFornecedorCertificacao(certificacao, request.dataInicio(), request.dataFim());
    }

    public ValidatedUpdateFornecedorCertificacao validateUpdateCertificacao(
            UpdateFornecedorCertificacaoRequest request) {
        validarDatas(request.dataInicio(), request.dataFim());
        return new ValidatedUpdateFornecedorCertificacao(request.dataInicio(), request.dataFim());
    }

    // ─── Privados ────────────────────────────────────────────────────────────────

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank())
            throw new ValidationException(ValidationErrorCode.NOME_NULL);
        if (nome.length() < 2)
            throw new ValidationException(ValidationErrorCode.NOME_FORNECEDOR_TOO_SHORT);
        if (nome.length() > 150)
            throw new ValidationException(ValidationErrorCode.NOME_FORNECEDOR_TOO_LONG);
    }

    private void validarNif(String nif) {
        if (nif == null || nif.isBlank())
            throw new ValidationException(ValidationErrorCode.NIF_NULL);
        if (!nif.matches("^\\d{9}$"))
            throw new ValidationException(ValidationErrorCode.NIF_INVALID);
        if (fornecedorRepository.existsByNif(nif))
            throw new ValidationException(ValidationErrorCode.NIF_ALREADY_EXISTS);
    }

    private void validarNifUpdate(UUID id, String nif) {
        if (nif == null || nif.isBlank())
            throw new ValidationException(ValidationErrorCode.NIF_NULL);
        if (!nif.matches("^\\d{9}$"))
            throw new ValidationException(ValidationErrorCode.NIF_INVALID);
        if (fornecedorRepository.existsByNifAndIdNot(nif, id))
            throw new ValidationException(ValidationErrorCode.NIF_ALREADY_EXISTS);
    }

    private void validarEmailFornecedor(String email) {
        if (email == null || email.isBlank()) return;
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
            throw new ValidationException(ValidationErrorCode.EMAIL_FORNECEDOR_INVALID_FORMAT);
    }

    private String validarTelefone(String telefone) {
        return PhoneUtils.validarENormalizar(telefone);
    }

    private void validarMorada(String morada) {
        if (morada == null || morada.isBlank()) return;
        if (morada.length() > 200)
            throw new ValidationException(ValidationErrorCode.MORADA_TOO_LONG);
    }

    private void validarCidade(String cidade) {
        if (cidade == null || cidade.isBlank()) return;
        if (cidade.length() > 100)
            throw new ValidationException(ValidationErrorCode.CIDADE_TOO_LONG);
    }

    private void validarDatas(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio == null)
            throw new ValidationException(ValidationErrorCode.DATA_INICIO_NULL);
        if (dataFim != null && dataFim.isBefore(dataInicio))
            throw new ValidationException(ValidationErrorCode.DATA_FIM_BEFORE_DATA_INICIO);
    }

    private FornecedorTipo parseTipo(UUID tipoId) {
        if (tipoId == null) return null;
        return fornecedorTipoRepository.findById(tipoId)
                .orElseThrow(() -> new ValidationException(ValidationErrorCode.EMPRESA_NOT_FOUND));
    }

    private List<ValidatedFornecedorCertificacao> parseCertificacoes(
            List<AddCertificacaoRequest> certificacoes) {
        if (certificacoes == null || certificacoes.isEmpty()) return new ArrayList<>();

        List<ValidatedFornecedorCertificacao> validated = new ArrayList<>();
        for (AddCertificacaoRequest cert : certificacoes) {
            Certificacao certificacao = certificacaoRepository.findById(cert.certificacaoId())
                    .orElseThrow(() -> new ValidationException(ValidationErrorCode.EMPRESA_NOT_FOUND));
            validarDatas(cert.dataInicio(), cert.dataFim());
            validated.add(new ValidatedFornecedorCertificacao(certificacao, cert.dataInicio(), cert.dataFim()));
        }
        return validated;
    }
}