package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.pallet_tipo.*;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.pallet_tipo.PalletTipoErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.pallet_tipo.PalletTipoException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.PalletTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.PalletTipoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PalletTipoService {

    private final PalletTipoRepository repository;

    public PalletTipoService(PalletTipoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PalletTipoResponse create(CreatePalletTipoRequest info) {
        if (repository.existsByNomeIgnoreCase(info.nome()))
            throw new PalletTipoException(PalletTipoErrorCode.NOME_ALREADY_EXISTS);

        if (repository.existsByCapacidadeKg(info.capacidadeKg()))
            throw new PalletTipoException(PalletTipoErrorCode.CAPACIDADE_ALREADY_EXISTS);

        try {
            PalletTipo pallet = new PalletTipo(info.nome(), info.capacidadeKg());
            return toResponse(repository.save(pallet));
        } catch (Exception e) {
            throw new PalletTipoException(PalletTipoErrorCode.PALLET_TIPO_CREATE_FAILED);
        }
    }

    public PalletTipoResponse findById(UUID id) {
        return repository.findByIdAndIsActiveTrue(id)
                .map(this::toResponse)
                .orElseThrow(() -> new PalletTipoException(PalletTipoErrorCode.PALLET_TIPO_NOT_FOUND));
    }

    public Page<PalletTipoResponse> findAllActive(Pageable pageable) {
        return repository.findAllByIsActiveTrue(pageable)
                .map(this::toResponse);
    }

    public Page<PalletTipoResponse> findAllInactive(Pageable pageable) {
        return repository.findAllByIsActiveFalse(pageable)
                .map(this::toResponse);
    }

    @Transactional
    public PalletTipoResponse update(UUID id, UpdatePalletTipoRequest info) {
        PalletTipo pallet = repository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new PalletTipoException(PalletTipoErrorCode.PALLET_TIPO_NOT_FOUND));

        if (repository.existsByNomeIgnoreCaseAndIdNot(info.nome(), id))
            throw new PalletTipoException(PalletTipoErrorCode.NOME_ALREADY_EXISTS);

        try {
            pallet.setNome(info.nome());
            pallet.setCapacidadeKg(info.capacidadeKg());
            return toResponse(repository.save(pallet));
        } catch (Exception e) {
            throw new PalletTipoException(PalletTipoErrorCode.PALLET_TIPO_UPDATE_FAILED);
        }
    }

    @Transactional
    public PalletTipoResponse reactivate(UUID id) {
        PalletTipo pallet = repository.findById(id)
                .orElseThrow(() -> new PalletTipoException(PalletTipoErrorCode.PALLET_TIPO_NOT_FOUND));

        if (pallet.isActive()) {
            throw new PalletTipoException(PalletTipoErrorCode.PALLET_TIPO_ALREADY_ACTIVE);
        }

        pallet.setActive(true);
        pallet.setDeletedAt(null);
        return toResponse(repository.save(pallet));
    }

    @Transactional
    public void softDelete(UUID id) {
        PalletTipo pallet = repository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new PalletTipoException(PalletTipoErrorCode.PALLET_TIPO_NOT_FOUND));
        pallet.softDelete();
        repository.save(pallet);
    }

    private PalletTipoResponse toResponse(PalletTipo pallet) {
        return new PalletTipoResponse(
                pallet.getId(),
                pallet.getNome(),
                pallet.getCapacidadeKg(),
                pallet.isActive(),
                pallet.getCreatedAt(),
                pallet.getUpdatedAt()
        );
    }
}