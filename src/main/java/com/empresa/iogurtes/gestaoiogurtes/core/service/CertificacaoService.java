package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.CertificacaoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.CreateCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.UpdateCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.CertificacaoErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.CertificacaoException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Certificacao;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.CertificacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CertificacaoService {

    private final CertificacaoRepository certificacaoRepository;

    public CertificacaoService(CertificacaoRepository certificacaoRepository) {
        this.certificacaoRepository = certificacaoRepository;
    }

    @Transactional
    public CertificacaoResponse createCertificacao(CreateCertificacaoRequest info) {
        if (certificacaoRepository.existsByNomeAndIsActiveIsTrue(info.nome()))
            throw new ValidationException(ValidationErrorCode.NOME_CERTIFICACAO_ALREADY_EXISTS);

        try {
            Certificacao certificacao = new Certificacao(info.nome(), info.descricao());
            return toResponse(certificacaoRepository.save(certificacao));
        } catch (Exception e) {
            throw new CertificacaoException(CertificacaoErrorCode.CERTIFICACAO_CREATE_FAILED);
        }
    }

    public CertificacaoResponse findById(UUID id) {
        return certificacaoRepository.findByIdAndIsActiveIsTrue(id)
                .map(this::toResponse)
                .orElseThrow(() -> new CertificacaoException(CertificacaoErrorCode.CERTIFICACAO_NOT_FOUND));
    }

    public Page<CertificacaoResponse> findAll(Pageable pageable) {
        return certificacaoRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public Page<CertificacaoResponse> findAllActive(Pageable pageable) {
        return certificacaoRepository.findAllByIsActiveTrue(pageable)
                .map(this::toResponse);
    }

    public Page<CertificacaoResponse> findAllInactive(Pageable pageable) {
        return certificacaoRepository.findAllByIsActiveFalse(pageable)
                .map(this::toResponse);
    }

    @Transactional
    public CertificacaoResponse updateCertificacao(UUID id, UpdateCertificacaoRequest info) {
        Certificacao certificacao = certificacaoRepository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new CertificacaoException(CertificacaoErrorCode.CERTIFICACAO_NOT_FOUND));

        // retorna true se tiver certificacao com id diferente com o mesmo nome
        if (certificacaoRepository.existsByNomeAndIdNot(info.nome(), id))
            throw new ValidationException(ValidationErrorCode.NOME_CERTIFICACAO_ALREADY_EXISTS_UPDATE);
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
        Certificacao certificacao = certificacaoRepository.findByIdAndIsActiveIsTrue(id)
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