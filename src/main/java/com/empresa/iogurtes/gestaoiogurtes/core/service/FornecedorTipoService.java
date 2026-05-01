package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.*;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.FornecedorTipoErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.FornecedorTipoException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorTipoRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.FornecedorTipoValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FornecedorTipoService {

    private final FornecedorTipoRepository fornecedorTipoRepository;
    private final FornecedorRepository fornecedorRepository;
    private final FornecedorTipoValidator fornecedorTipoValidator;

    public FornecedorTipoService(FornecedorTipoRepository fornecedorTipoRepository,
                                 FornecedorRepository fornecedorRepository,
                                 FornecedorTipoValidator fornecedorTipoValidator) {
        this.fornecedorTipoRepository = fornecedorTipoRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorTipoValidator = fornecedorTipoValidator;
    }

    // ─── Criação ────────────────────────────────────────────────────────────────

    @Transactional
    public FornecedorTipoResponse createFornecedorTipo(CreateFornecedorTipoRequest request) {
        ValidatedFornecedorTipo info = fornecedorTipoValidator.validateCreateFornecedorTipo(request);

        try {
            FornecedorTipo tipo = new FornecedorTipo(info.nome(), info.descricao());
            return toResponse(fornecedorTipoRepository.save(tipo));
        } catch (Exception e) {
            throw new FornecedorTipoException(FornecedorTipoErrorCode.FORNECEDOR_TIPO_CREATE_FAILED);
        }
    }

    // ─── Leitura ────────────────────────────────────────────────────────────────

    public FornecedorTipoResponse findById(UUID id) {
        return fornecedorTipoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new FornecedorTipoException(FornecedorTipoErrorCode.FORNECEDOR_TIPO_NOT_FOUND));
    }

    public List<FornecedorTipoResponse> findAllActive() {
        return fornecedorTipoRepository.findAllByIsActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<FornecedorTipoResponse> findAllInactive() {
        return fornecedorTipoRepository.findAllByIsActiveFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ─── Update ─────────────────────────────────────────────────────────────────

    @Transactional
    public FornecedorTipoResponse updateFornecedorTipo(UUID id, UpdateFornecedorTipoRequest request) {
        FornecedorTipo tipo = fornecedorTipoRepository.findById(id)
                .orElseThrow(() -> new FornecedorTipoException(FornecedorTipoErrorCode.FORNECEDOR_TIPO_NOT_FOUND));

        ValidatedUpdateFornecedorTipo info = fornecedorTipoValidator.validateUpdateFornecedorTipo(id, request);

        try {
            tipo.setNome(info.nome());
            tipo.setDescricao(info.descricao());
            return toResponse(fornecedorTipoRepository.save(tipo));
        } catch (Exception e) {
            throw new FornecedorTipoException(FornecedorTipoErrorCode.FORNECEDOR_TIPO_UPDATE_FAILED);
        }
    }

    // ─── Delete ─────────────────────────────────────────────────────────────────

    @Transactional
    public void softDelete(UUID id) {
        FornecedorTipo tipo = fornecedorTipoRepository.findById(id)
                .orElseThrow(() -> new FornecedorTipoException(FornecedorTipoErrorCode.FORNECEDOR_TIPO_NOT_FOUND));

        if (fornecedorRepository.existsByTipo_IdAndIsActiveTrue(id))
            throw new FornecedorTipoException(FornecedorTipoErrorCode.FORNECEDOR_TIPO_HAS_FORNECEDORES);

        tipo.softDelete();
        fornecedorTipoRepository.save(tipo);
    }

    // ─── Mapper ─────────────────────────────────────────────────────────────────

    private FornecedorTipoResponse toResponse(FornecedorTipo tipo) {
        return new FornecedorTipoResponse(
                tipo.getId(),
                tipo.getNome(),
                tipo.getDescricao(),
                tipo.isActive(),
                tipo.getCreatedAt()
        );
    }
}