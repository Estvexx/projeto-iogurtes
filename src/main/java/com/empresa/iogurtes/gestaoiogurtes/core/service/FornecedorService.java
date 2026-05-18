// FornecedorService.java
package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.*;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.FornecedorTipoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores.*;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.FornecedorCertificacaoErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.FornecedorCertificacaoException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.FornecedorErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.FornecedorException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Fornecedor;
import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorCertificacao;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorCertificacaoRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.FornecedorValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final FornecedorCertificacaoRepository fornecedorCertificacaoRepository;
    //private final MateriaPrimaFornecedorRepository materiaPrimaFornecedorRepository;
    //private final EncomendaMpRepository encomendaMpRepository;
    private final FornecedorValidator fornecedorValidator;

    public FornecedorService(FornecedorRepository fornecedorRepository,
                             FornecedorCertificacaoRepository fornecedorCertificacaoRepository,
                             //MateriaPrimaFornecedorRepository materiaPrimaFornecedorRepository,
                             //EncomendaMpRepository encomendaMpRepository,
                             FornecedorValidator fornecedorValidator) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorCertificacaoRepository = fornecedorCertificacaoRepository;
        //this.materiaPrimaFornecedorRepository = materiaPrimaFornecedorRepository;
        //this.encomendaMpRepository = encomendaMpRepository;
        this.fornecedorValidator = fornecedorValidator;
    }

    // ─── Criação ────────────────────────────────────────────────────────────────

    @Transactional
    public FornecedorResponse createFornecedor(CreateFornecedorRequest request) {
        ValidatedFornecedor info = fornecedorValidator.validateCreateFornecedor(request);

        try {
            Fornecedor fornecedor = new Fornecedor(
                    info.nome(), info.nif(), info.email(),
                    info.telefone(), info.morada(), info.cidade(), info.tipo());

            fornecedorRepository.save(fornecedor);

            for (ValidatedFornecedorCertificacao cert : info.certificacoes()) {
                FornecedorCertificacao fc = new FornecedorCertificacao(
                        fornecedor, cert.certificacao(), cert.dataInicio(), cert.dataFim());
                fornecedorCertificacaoRepository.save(fc);
            }

            return toResponse(fornecedor);
        } catch (Exception e) {
            throw new FornecedorException(FornecedorErrorCode.FORNECEDOR_CREATE_FAILED);
        }
    }

    // ─── Leitura ────────────────────────────────────────────────────────────────

    public FornecedorResponse findById(UUID id) {
        return fornecedorRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new FornecedorException(FornecedorErrorCode.FORNECEDOR_NOT_FOUND));
    }

    public List<FornecedorResponse> findAllActive() {
        return fornecedorRepository.findAllByIsActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<FornecedorResponse> findAllInactive() {
        return fornecedorRepository.findAllByIsActiveFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<FornecedorResponse> findAllByTipo(UUID tipoId) {
        return fornecedorRepository.findAllByTipo_IdAndIsActiveTrue(tipoId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ─── Update ─────────────────────────────────────────────────────────────────

    @Transactional
    public FornecedorResponse updateFornecedor(UUID id, UpdateFornecedorRequest request) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new FornecedorException(FornecedorErrorCode.FORNECEDOR_NOT_FOUND));

        ValidatedUpdateFornecedor info = fornecedorValidator.validateUpdateFornecedor(id, request);

        try {
            fornecedor.setNome(info.nome());
            fornecedor.setEmail(info.email());
            fornecedor.setTelefone(info.telefone());
            fornecedor.setMorada(info.morada());
            fornecedor.setCidade(info.cidade());
            fornecedor.setTipo(info.tipo());
            return toResponse(fornecedorRepository.save(fornecedor));
        } catch (Exception e) {
            throw new FornecedorException(FornecedorErrorCode.FORNECEDOR_UPDATE_FAILED);
        }
    }

    // ─── Certificacoes ───────────────────────────────────────────────────────────

    @Transactional
    public FornecedorCertificacaoResponse addCertificacao(UUID fornecedorId,
                                                          AddCertificacaoRequest request) {
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new FornecedorException(FornecedorErrorCode.FORNECEDOR_NOT_FOUND));

        ValidatedFornecedorCertificacao info = fornecedorValidator
                .validateAddCertificacao(fornecedorId, request);

        try {
            FornecedorCertificacao fc = new FornecedorCertificacao(
                    fornecedor, info.certificacao(), info.dataInicio(), info.dataFim());
            return toCertificacaoResponse(fornecedorCertificacaoRepository.save(fc));
        } catch (Exception e) {
            throw new FornecedorCertificacaoException(
                    FornecedorCertificacaoErrorCode.FORNECEDOR_CERTIFICACAO_CREATE_FAILED);
        }
    }

    @Transactional
    public FornecedorCertificacaoResponse updateCertificacao(UUID fornecedorCertificacaoId,
                                                             UpdateFornecedorCertificacaoRequest request) {
        FornecedorCertificacao fc = fornecedorCertificacaoRepository.findById(fornecedorCertificacaoId)
                .orElseThrow(() -> new FornecedorCertificacaoException(
                        FornecedorCertificacaoErrorCode.FORNECEDOR_CERTIFICACAO_NOT_FOUND));

        ValidatedUpdateFornecedorCertificacao info = fornecedorValidator
                .validateUpdateCertificacao(request);

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
        FornecedorCertificacao fc = fornecedorCertificacaoRepository.findById(fornecedorCertificacaoId)
                .orElseThrow(() -> new FornecedorCertificacaoException(
                        FornecedorCertificacaoErrorCode.FORNECEDOR_CERTIFICACAO_NOT_FOUND));
        fc.softDelete();
        fornecedorCertificacaoRepository.save(fc);
    }

    // ─── Delete ─────────────────────────────────────────────────────────────────
/*
    @Transactional
    public void softDelete(UUID id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new FornecedorException(FornecedorErrorCode.FORNECEDOR_NOT_FOUND));

        if (encomendaMpRepository.existsByFornecedor_IdAndEntregue(id))
            throw new FornecedorException(FornecedorErrorCode.FORNECEDOR_HAS_ENCOMENDAS_PENDENTES);

        // softdelete nas materias fornecedores
        materiaPrimaFornecedorRepository.findAllByFornecedor_IdAndIsActiveTrue(id)
                .forEach(mf -> {
                    mf.softDelete();
                    materiaPrimaFornecedorRepository.save(mf);
                });

        fornecedor.softDelete();
        fornecedorRepository.save(fornecedor);
    }*/

    // ─── Mappers ─────────────────────────────────────────────────────────────────

    private FornecedorResponse toResponse(Fornecedor fornecedor) {
        List<FornecedorCertificacaoResponse> certificacoes = fornecedorCertificacaoRepository
                .findAllByFornecedor_Id(fornecedor.getId())
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
                fc.getCertificacao().getNome(),
                fc.getDataInicio(),
                fc.getDataFim(),
                fc.isActive()
        );
    }
}