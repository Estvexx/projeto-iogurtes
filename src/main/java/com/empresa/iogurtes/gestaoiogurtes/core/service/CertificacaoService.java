package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.*;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.CertificacaoErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.CertificacaoException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Certificacao;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.CertificacaoRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.CertificacaoValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CertificacaoService {

    private final CertificacaoRepository certificacaoRepository;
    private final CertificacaoValidator certificacaoValidator;

    public CertificacaoService(CertificacaoRepository certificacaoRepository,
                               CertificacaoValidator certificacaoValidator) {
        this.certificacaoRepository = certificacaoRepository;
        this.certificacaoValidator = certificacaoValidator;
    }

    @Transactional
    public CertificacaoResponse createCertificacao(CreateCertificacaoRequest request) {
        ValidatedCertificacao info = certificacaoValidator.validateCreateCertificacao(request);

        try {
            Certificacao certificacao = new Certificacao(info.nome(), info.descricao());
            return toResponse(certificacaoRepository.save(certificacao));
        } catch (Exception e) {
            throw new CertificacaoException(CertificacaoErrorCode.CERTIFICACAO_CREATE_FAILED);
        }
    }

    public CertificacaoResponse findById(UUID id) {
        return certificacaoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new CertificacaoException(CertificacaoErrorCode.CERTIFICACAO_NOT_FOUND));
    }

    public List<CertificacaoResponse> findAllActive() {
        return certificacaoRepository.findAllByIsActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<CertificacaoResponse> findAllInactive() {
        return certificacaoRepository.findAllByIsActiveFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CertificacaoResponse updateCertificacao(UUID id, UpdateCertificacaoRequest request) {
        Certificacao certificacao = certificacaoRepository.findById(id)
                .orElseThrow(() -> new CertificacaoException(CertificacaoErrorCode.CERTIFICACAO_NOT_FOUND));

        ValidatedUpdateCertificacao info = certificacaoValidator.validateUpdateCertificacao(id, request);

        try {
            certificacao.setNome(info.nome());
            certificacao.setDescricao(info.descricao());
            return toResponse(certificacaoRepository.save(certificacao));
        } catch (Exception e) {
            throw new CertificacaoException(CertificacaoErrorCode.CERTIFICACAO_UPDATE_FAILED);
        }
    }

    @Transactional
    public void softDelete(UUID id) {
        Certificacao certificacao = certificacaoRepository.findById(id)
                .orElseThrow(() -> new CertificacaoException(CertificacaoErrorCode.CERTIFICACAO_NOT_FOUND));
        certificacao.softDelete();
        certificacaoRepository.save(certificacao);
    }

    private CertificacaoResponse toResponse(Certificacao certificacao) {
        return new CertificacaoResponse(
                certificacao.getId(),
                certificacao.getNome(),
                certificacao.getDescricao(),
                certificacao.isActive(),
                certificacao.getCreatedAt()
        );
    }
}