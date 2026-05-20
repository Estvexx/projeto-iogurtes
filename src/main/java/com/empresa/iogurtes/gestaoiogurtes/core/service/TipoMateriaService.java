package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.materias_tipo.*;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima.TipoMateriaErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima.TipoMateriaException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.TipoMateria;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MateriaPrimaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.TipoMateriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TipoMateriaService {

    private final TipoMateriaRepository repository;
    private final MateriaPrimaRepository materiaPrimaRepository;

    public TipoMateriaService(TipoMateriaRepository repository, MateriaPrimaRepository materiaPrimaRepository) {
        this.repository = repository;
        this.materiaPrimaRepository = materiaPrimaRepository;
    }

    @Transactional
    public MateriaTipoResponse create(CreateTipoMateriaRequest info) {
        if (repository.existsByNomeIgnoreCase(info.nome()))
            throw new TipoMateriaException(TipoMateriaErrorCode.NOME_ALREADY_EXISTS);

        try {
            TipoMateria tipo = new TipoMateria(info.nome(), info.descricao(), info.taxaIva());
            return toResponse(repository.save(tipo));
        } catch (Exception e) {
            throw new TipoMateriaException(TipoMateriaErrorCode.TIPO_MATERIA_CREATE_FAILED);
        }
    }

    public MateriaTipoResponse findById(UUID id) {
        return repository.findByIdAndIsActiveIsTrue(id)
                .map(this::toResponse)
                .orElseThrow(() -> new TipoMateriaException(TipoMateriaErrorCode.TIPO_MATERIA_NOT_FOUND));
    }

    public Page<MateriaTipoResponse> findAllActive(Pageable pageable) {
        return repository.findAllByIsActiveTrue(pageable)
                .map(this::toResponse);
    }

    public Page<MateriaTipoResponse> findAllInactive(Pageable pageable) {
        return repository.findAllByIsActiveFalse(pageable)
                .map(this::toResponse);
    }

    @Transactional
    public MateriaTipoResponse update(UUID id, UpdateTipoMateriaRequest info) {
        TipoMateria tipo = repository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new TipoMateriaException(TipoMateriaErrorCode.TIPO_MATERIA_NOT_FOUND));

        if (repository.existsByNomeIgnoreCaseAndIdNot(info.nome(), id))
            throw new TipoMateriaException(TipoMateriaErrorCode.NOME_ALREADY_EXISTS);

        try {
            tipo.setNome(info.nome());
            tipo.setDescricao(info.descricao());
            tipo.setTaxaIva(info.taxaIva());
            return toResponse(repository.save(tipo));
        } catch (Exception e) {
            throw new TipoMateriaException(TipoMateriaErrorCode.TIPO_MATERIA_UPDATE_FAILED);
        }
    }

    @Transactional
    public void softDelete(UUID id) {
        TipoMateria tipo = repository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new TipoMateriaException(TipoMateriaErrorCode.TIPO_MATERIA_NOT_FOUND));

        if (materiaPrimaRepository.existsByTipo_IdAndIsActiveTrue(id)) {
            throw new TipoMateriaException(TipoMateriaErrorCode.TIPO_MATERIA_EM_USO);
        }

        tipo.softDelete();
        repository.save(tipo);
    }

    private MateriaTipoResponse toResponse(TipoMateria tipo) {
        return new MateriaTipoResponse(
                tipo.getId(),
                tipo.getNome(),
                tipo.getDescricao(),
                tipo.getTaxaIva(),
                tipo.isActive(),
                tipo.getCreatedAt()
        );
    }
}