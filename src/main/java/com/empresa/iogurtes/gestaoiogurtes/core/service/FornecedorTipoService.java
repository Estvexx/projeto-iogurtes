package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.CreateFornecedorTipoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.FornecedorTipoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.UpdateFornecedorTipoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.FornecedorTipoErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.FornecedorTipoException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorTipoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FornecedorTipoService {

    private final FornecedorTipoRepository fornecedorTipoRepository;
    private final FornecedorRepository fornecedorRepository;

    public FornecedorTipoService(FornecedorTipoRepository fornecedorTipoRepository,
                                 FornecedorRepository fornecedorRepository
                                 ) {
        this.fornecedorTipoRepository = fornecedorTipoRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public FornecedorTipoResponse createFornecedorTipo(CreateFornecedorTipoRequest info) {
        if (fornecedorTipoRepository.existsByNome(info.nome()))
            throw new ValidationException(ValidationErrorCode.NOME_FORNECEDOR_TIPO_ALREADY_EXISTS);

        try {
            FornecedorTipo tipo = new FornecedorTipo(info.nome(), info.descricao());
            return toResponse(fornecedorTipoRepository.save(tipo));
        } catch (Exception e) {
            throw new FornecedorTipoException(FornecedorTipoErrorCode.FORNECEDOR_TIPO_CREATE_FAILED);
        }
    }

    public FornecedorTipoResponse findById(UUID id) {
        return fornecedorTipoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new FornecedorTipoException(FornecedorTipoErrorCode.FORNECEDOR_TIPO_NOT_FOUND));
    }

    public Page<FornecedorTipoResponse> findAllActive(Pageable pageable) {
        return fornecedorTipoRepository.findAllByIsActiveTrue(pageable)
                .map(this::toResponse);
    }

    public Page<FornecedorTipoResponse> findAllInactive(Pageable pageable) {
        return fornecedorTipoRepository.findAllByIsActiveFalse(pageable)
                .map(this::toResponse);
    }

    @Transactional
    public FornecedorTipoResponse updateFornecedorTipo(UUID id, UpdateFornecedorTipoRequest info) {
        FornecedorTipo tipo = fornecedorTipoRepository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new FornecedorTipoException(FornecedorTipoErrorCode.FORNECEDOR_TIPO_NOT_FOUND));

        if (fornecedorTipoRepository.existsByNomeAndIdNot(info.nome(), id))
            throw new ValidationException(ValidationErrorCode.NOME_FORNECEDOR_TIPO_ALREADY_EXISTS_UPDATE);
        try {
            tipo.setNome(info.nome());
            tipo.setDescricao(info.descricao());
            return toResponse(fornecedorTipoRepository.save(tipo));
        } catch (Exception e) {
            throw new FornecedorTipoException(FornecedorTipoErrorCode.FORNECEDOR_TIPO_UPDATE_FAILED);
        }
    }

    @Transactional
    public void softDelete(UUID id) {
        FornecedorTipo tipo = fornecedorTipoRepository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new FornecedorTipoException(FornecedorTipoErrorCode.FORNECEDOR_TIPO_NOT_FOUND));

        if (fornecedorRepository.existsByTipo_IdAndIsActiveTrue(id))
            throw new FornecedorTipoException(FornecedorTipoErrorCode.FORNECEDOR_TIPO_HAS_FORNECEDORES);

        tipo.softDelete();
        fornecedorTipoRepository.save(tipo);
    }

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