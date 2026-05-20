package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.materiaprima.*;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.materias_tipo.MateriaTipoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima.MateriaPrimaErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima.MateriaPrimaException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.MateriaFornecedor;
import com.empresa.iogurtes.gestaoiogurtes.core.model.MateriaPrima;
import com.empresa.iogurtes.gestaoiogurtes.core.model.TipoMateria;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MateriaFornecedorRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MateriaPrimaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.ProdutoMateriaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.TipoMateriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MateriaPrimaService {

    private final MateriaPrimaRepository materiaPrimaRepository;
    private final MateriaFornecedorRepository materiaFornecedorRepository;
    private final TipoMateriaRepository tipoMateriaRepository;
    private final ProdutoMateriaRepository produtoMateriaRepository;

    public MateriaPrimaService(MateriaPrimaRepository materiaPrimaRepository,
                               MateriaFornecedorRepository materiaFornecedorRepository,
                               TipoMateriaRepository tipoMateriaRepository,
                               ProdutoMateriaRepository produtoMateriaRepository) {
        this.materiaPrimaRepository = materiaPrimaRepository;
        this.materiaFornecedorRepository = materiaFornecedorRepository;
        this.tipoMateriaRepository = tipoMateriaRepository;
        this.produtoMateriaRepository = produtoMateriaRepository;
    }

    @Transactional
    public MateriaPrimaResponse createMateriaPrima(CreateMateriaPrimaRequest info) {
        if (materiaPrimaRepository.existsByNomeIgnoreCase(info.nome()))
            throw new MateriaPrimaException(MateriaPrimaErrorCode.NOME_ALREADY_EXISTS);

        TipoMateria tipo = tipoMateriaRepository.findById(info.tipoId())
                .orElseThrow(() -> new ValidationException(ValidationErrorCode.TIPO_NOT_FOUND));

        try {
            MateriaPrima materia = new MateriaPrima(
                    info.nome(), info.unidade(), info.stockMinimo(), tipo);
            return toResponse(materiaPrimaRepository.save(materia));
        } catch (Exception e) {
            throw new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_CREATE_FAILED);
        }
    }

    public MateriaPrimaResponse findById(UUID id) {
        return materiaPrimaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));
    }

    public Page<MateriaPrimaResponse> findAllActive(Pageable pageable) {
        return materiaPrimaRepository.findAllByIsActiveTrue(pageable)
                .map(this::toResponse);
    }

    public Page<MateriaPrimaResponse> findAllInactive(Pageable pageable) {
        return materiaPrimaRepository.findAllByIsActiveFalse(pageable)
                .map(this::toResponse);
    }

    @Transactional
    public MateriaPrimaResponse updateMateriaPrima(UUID id, UpdateMateriaPrimaRequest info) {
        MateriaPrima materia = materiaPrimaRepository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));

        if (materiaPrimaRepository.existsByNomeIgnoreCaseAndIdNot(info.nome(), id))
            throw new MateriaPrimaException(MateriaPrimaErrorCode.NOME_ALREADY_EXISTS);

        TipoMateria tipo = tipoMateriaRepository.findById(info.tipoId())
                .orElseThrow(() -> new ValidationException(ValidationErrorCode.TIPO_NOT_FOUND));

        try {
            materia.setNome(info.nome());
            materia.setUnidade(info.unidade());
            materia.setStockMinimo(info.stockMinimo());
            materia.setTipo(tipo);
            return toResponse(materiaPrimaRepository.save(materia));
        } catch (Exception e) {
            throw new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_UPDATE_FAILED);
        }
    }

    @Transactional
    public void softDelete(UUID id) {
        MateriaPrima materia = materiaPrimaRepository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));

        if (produtoMateriaRepository.existsByMateria_IdAndIsActiveTrue(id)) {
            throw new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_EM_USO);
        }

        List<MateriaFornecedor> associacoes = materiaFornecedorRepository.findAllByMateria_IdAndIsActiveTrue(id);
        associacoes.forEach(mf -> {
            mf.softDelete();
            materiaFornecedorRepository.save(mf);
        });

        materia.softDelete();
        materiaPrimaRepository.save(materia);
    }


    private MateriaPrimaResponse toResponse(MateriaPrima materia) {
        MateriaTipoResponse tipoResponse = materia.getTipo() != null
                ? new MateriaTipoResponse(
                materia.getTipo().getId(),
                materia.getTipo().getNome(),
                materia.getTipo().getDescricao(),
                materia.getTipo().getTaxaIva(),
                materia.getTipo().isActive(),
                materia.getTipo().getCreatedAt())
                : null;

        return new MateriaPrimaResponse(
                materia.getId(),
                materia.getNome(),
                materia.getUnidade(),
                materia.getStockAtual(),
                materia.getStockMinimo(),
                tipoResponse,
                materia.isActive(),
                materia.getCreatedAt(),
                materia.getUpdatedAt()
        );
    }
}