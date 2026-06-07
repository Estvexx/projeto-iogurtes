// FornecedorService.java
package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.AddCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.FornecedorCertificacaoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.UpdateFornecedorCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.FornecedorTipoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores.CreateFornecedorRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores.FornecedorResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores.UpdateFornecedorRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.FornecedorCertificacaoErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.FornecedorCertificacaoException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.FornecedorErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.FornecedorException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Certificacao;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Fornecedor;
import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorCertificacao;
import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomendaMP;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.*;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.FornecedorValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final FornecedorCertificacaoRepository fornecedorCertificacaoRepository;
    private final FornecedorTipoRepository fornecedorTipoRepository;
    private final CertificacaoRepository certificacaoRepository;
    private final MateriaFornecedorRepository materiaFornecedorRepository;
    private final EncomendaMPRepository encomendaMpRepository;
    private final FornecedorValidator fornecedorValidator;

    public FornecedorService(FornecedorRepository fornecedorRepository,
                             FornecedorCertificacaoRepository fornecedorCertificacaoRepository,
                             CertificacaoRepository certificacaoRepository,
                             MateriaFornecedorRepository materiaFornecedorRepository,
                             EncomendaMPRepository encomendaMpRepository,
                             FornecedorTipoRepository fornecedorTipoRepository,
                             FornecedorValidator fornecedorValidator) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorCertificacaoRepository = fornecedorCertificacaoRepository;
        this.materiaFornecedorRepository = materiaFornecedorRepository;
        this.encomendaMpRepository = encomendaMpRepository;
        this. certificacaoRepository = certificacaoRepository;
        this.fornecedorTipoRepository = fornecedorTipoRepository;
        this.fornecedorValidator = fornecedorValidator;
    }

    @Transactional
    public FornecedorResponse createFornecedor(CreateFornecedorRequest info) {
        if (fornecedorRepository.existsByNif(info.nif()))
            throw new ValidationException(ValidationErrorCode.NIF_ALREADY_EXISTS);

        FornecedorTipo tipo = fornecedorTipoRepository.findById(info.tipoId())
                .orElseThrow(() -> new ValidationException(ValidationErrorCode.TIPO_NOT_FOUND));

        String telefone = fornecedorValidator.validarTelefone(info.telefone());

        List<Certificacao> certificacoes = new ArrayList<>();
        for (AddCertificacaoRequest cert : info.certificacoes()) {
            Certificacao certificacao = certificacaoRepository.findById(cert.certificacaoId())
                    .orElseThrow(() -> new ValidationException(ValidationErrorCode.CERTIFICACAO_NOT_FOUND));
            fornecedorValidator.validarDatas(cert.dataInicio(), cert.dataFim());
            certificacoes.add(certificacao);
        }

        try {
            Fornecedor fornecedor = new Fornecedor(
                    info.nome(), info.nif(), info.email(),
                    telefone, info.morada(), info.cidade(), tipo);

            fornecedorRepository.save(fornecedor);

            for (int i = 0; i < info.certificacoes().size(); i++) {
                AddCertificacaoRequest cert = info.certificacoes().get(i);
                Certificacao certificacao = certificacoes.get(i);
                FornecedorCertificacao fc = new FornecedorCertificacao(
                        fornecedor, certificacao, cert.dataInicio(), cert.dataFim());
                fornecedorCertificacaoRepository.save(fc);
            }

            return toResponse(fornecedor);
        } catch (Exception e) {
            throw new FornecedorException(FornecedorErrorCode.FORNECEDOR_CREATE_FAILED);
        }
    }

    public FornecedorResponse findById(UUID id) {
        return fornecedorRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new FornecedorException(FornecedorErrorCode.FORNECEDOR_NOT_FOUND));
    }

    public Page<FornecedorResponse> findAllActive(Pageable pageable) {
        return fornecedorRepository.findAllByIsActiveTrue(pageable)
                .map(this::toResponse);
    }

    public Page<FornecedorResponse> findAllInactive(Pageable pageable) {
        return fornecedorRepository.findAllByIsActiveFalse(pageable)
                .map(this::toResponse);
    }

    public Page<FornecedorResponse> findAllByTipo(UUID tipoId, Pageable pageable) {
        return fornecedorRepository.findAllByTipo_IdAndIsActiveTrue(tipoId, pageable)
                .map(this::toResponse);
    }

    public Page<FornecedorCertificacaoResponse> findAllCertificacoes(Pageable pageable) {
        return fornecedorCertificacaoRepository.findAllByIsActiveTrue(pageable)
                .map(this::toCertificacaoResponse);
    }

    @Transactional
    public FornecedorResponse updateFornecedor(UUID id, UpdateFornecedorRequest info) {
        Fornecedor fornecedor = fornecedorRepository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new FornecedorException(FornecedorErrorCode.FORNECEDOR_NOT_FOUND));

        if (fornecedorRepository.existsByNifAndIdNot(info.nif(), id))
            throw new ValidationException(ValidationErrorCode.NIF_ALREADY_EXISTS);

        FornecedorTipo tipo = fornecedorTipoRepository.findByIdAndIsActiveIsTrue(info.tipoId())
                .orElseThrow(() -> new ValidationException(ValidationErrorCode.TIPO_NOT_FOUND));

        String telefone = fornecedorValidator.validarTelefone(info.telefone());

        try {
            fornecedor.setNome(info.nome());
            fornecedor.setEmail(info.email());
            fornecedor.setTelefone(telefone);
            fornecedor.setMorada(info.morada());
            fornecedor.setCidade(info.cidade());
            fornecedor.setTipo(tipo);
            return toResponse(fornecedorRepository.save(fornecedor));
        } catch (Exception e) {
            throw new FornecedorException(FornecedorErrorCode.FORNECEDOR_UPDATE_FAILED);
        }
    }

    @Transactional
    public FornecedorCertificacaoResponse addCertificacao(UUID fornecedorId,
                                                          AddCertificacaoRequest info) {

        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new FornecedorException(FornecedorErrorCode.FORNECEDOR_NOT_FOUND));

        // Verificar se o fornecedor já tem esta certificação
        if (fornecedorCertificacaoRepository.existsByFornecedor_IdAndCertificacao_Id(
                fornecedorId, info.certificacaoId()))
            throw new ValidationException(ValidationErrorCode.NOME_CERTIFICACAO_ALREADY_EXISTS);

        Certificacao certificacao = certificacaoRepository.findById(info.certificacaoId())
                .orElseThrow(() -> new ValidationException(ValidationErrorCode.CERTIFICACAO_NOT_FOUND));

        fornecedorValidator.validarDatas(info.dataInicio(), info.dataFim());

        try {
            FornecedorCertificacao fc = new FornecedorCertificacao(
                    fornecedor, certificacao, info.dataInicio(), info.dataFim());
            return toCertificacaoResponse(fornecedorCertificacaoRepository.save(fc));
        } catch (Exception e) {
            throw new FornecedorCertificacaoException(
                    FornecedorCertificacaoErrorCode.FORNECEDOR_CERTIFICACAO_CREATE_FAILED);
        }
    }

    @Transactional
    public FornecedorCertificacaoResponse updateCertificacao(UUID fornecedorCertificacaoId,
                                                             UpdateFornecedorCertificacaoRequest info) {

        FornecedorCertificacao fc = fornecedorCertificacaoRepository.findById(fornecedorCertificacaoId)
                .orElseThrow(() -> new FornecedorCertificacaoException(
                        FornecedorCertificacaoErrorCode.FORNECEDOR_CERTIFICACAO_NOT_FOUND));

        fornecedorValidator.validarDatas(info.dataInicio(), info.dataFim());

        try {
            fc.setDataInicio(info.dataInicio());
            fc.setDataFim(info.dataFim());
            return toCertificacaoResponse(fornecedorCertificacaoRepository.save(fc));
        } catch (Exception e) {
            throw new FornecedorCertificacaoException(
                    FornecedorCertificacaoErrorCode.FORNECEDOR_CERTIFICACAO_UPDATE_FAILED);
        }
    }

    @Transactional
    public void removeCertificacao(UUID fornecedorCertificacaoId) {
        FornecedorCertificacao fc = fornecedorCertificacaoRepository.findByIdAndIsActiveIsTrue(fornecedorCertificacaoId)
                .orElseThrow(() -> new FornecedorCertificacaoException(
                        FornecedorCertificacaoErrorCode.FORNECEDOR_CERTIFICACAO_NOT_FOUND));
        fc.softDelete();
        fornecedorCertificacaoRepository.save(fc);
    }

    @Transactional
    public void softDelete(UUID id) {
        Fornecedor fornecedor = fornecedorRepository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new FornecedorException(FornecedorErrorCode.FORNECEDOR_NOT_FOUND));

        if (encomendaMpRepository.existsByFornecedor_IdAndEstadoNotIn(id,
                List.of(EstadoEncomendaMP.RECEBIDA, EstadoEncomendaMP.CANCELADA)))
            throw new FornecedorException(FornecedorErrorCode.FORNECEDOR_HAS_ENCOMENDAS_PENDENTES);

        // Soft delete nas certificações do fornecedor
        fornecedorCertificacaoRepository.findAllByFornecedor_IdAndIsActiveTrue(id)
                .forEach(fc -> {
                    fc.softDelete();
                    fornecedorCertificacaoRepository.save(fc);
                });

        // Soft delete nas matérias primas do fornecedor
        materiaFornecedorRepository.findAllByFornecedor_IdAndIsActiveTrue(id)
                .forEach(mf -> {
                    mf.softDelete();
                    materiaFornecedorRepository.save(mf);
                });

        fornecedor.softDelete();
        fornecedorRepository.save(fornecedor);
    }

    // ─── Mappers ─────────────────────────────────────────────────────────────────

    private FornecedorResponse toResponse(Fornecedor fornecedor) {
        List<FornecedorCertificacaoResponse> certificacoes = fornecedorCertificacaoRepository
                .findAllByFornecedor_IdAndIsActiveTrue(fornecedor.getId())
                .stream()
                .map(this::toCertificacaoResponse)
                .toList();

        return new FornecedorResponse(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getNif(),
                fornecedor.getEmail(),
                fornecedor.getTelefone(),
                fornecedor.getMorada(),
                fornecedor.getCidade(),
                fornecedor.getTipo() != null ? new FornecedorTipoResponse(
                        fornecedor.getTipo().getId(),
                        fornecedor.getTipo().getNome(),
                        fornecedor.getTipo().getDescricao(),
                        fornecedor.getTipo().isActive(),
                        fornecedor.getTipo().getCreatedAt()) : null,
                certificacoes,
                fornecedor.isActive(),
                fornecedor.getCreatedAt()
        );
    }

    private FornecedorCertificacaoResponse toCertificacaoResponse(FornecedorCertificacao fc) {
        return new FornecedorCertificacaoResponse(
                fc.getId(),
                fc.getFornecedor().getNome(),
                fc.getCertificacao().getNome(),
                fc.getDataInicio(),
                fc.getDataFim(),
                fc.isActive()
        );
    }


}


