package com.empresa.iogurtes.gestaoiogurtes.core.service;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.materiafornecedor.MateriaFornecedorErrorCode;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.materiaprima.CreateMateriaFornecedorRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.materiaprima.MateriaFornecedorResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.materiaprima.UpdateMateriaFornecedorRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima.MateriaFornecedorException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima.MateriaPrimaErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima.MateriaPrimaException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.moeda.MoedaErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.moeda.MoedaException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Fornecedor;
import com.empresa.iogurtes.gestaoiogurtes.core.model.MateriaFornecedor;
import com.empresa.iogurtes.gestaoiogurtes.core.model.MateriaPrima;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Moeda;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MateriaFornecedorRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MateriaPrimaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MoedaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
public class MateriaFornecedorService
{

    private final MateriaFornecedorRepository materiaFornecedorRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final MoedaRepository moedaRepository;

    public MateriaFornecedorService(MateriaFornecedorRepository materiaFornecedorRepository,
                                    MateriaPrimaRepository materiaPrimaRepository,
                                    FornecedorRepository fornecedorRepository,
                                    MoedaRepository moedaRepository)
    {
        this.materiaFornecedorRepository = materiaFornecedorRepository;
        this.materiaPrimaRepository = materiaPrimaRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.moedaRepository = moedaRepository;
    }

    @Transactional
    public MateriaFornecedorResponse createMateriaFornecedor(UUID materiaId, CreateMateriaFornecedorRequest info)
    {
        MateriaPrima materia = materiaPrimaRepository.findByIdAndIsActiveIsTrue(materiaId)
                .orElseThrow(()->new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));

        Fornecedor fornecedor = fornecedorRepository.findByIdAndIsActiveIsTrue(info.fornecedorId())
                .orElseThrow(()->new MateriaFornecedorException(MateriaFornecedorErrorCode.FORNECEDOR_INACTIVE));

        Moeda moeda = moedaRepository.findByIdAndIsActiveTrue(info.moedaId())
                .orElseThrow(()->new MoedaException(MoedaErrorCode.MOEDA_NOT_FOUND));

        if (materiaFornecedorRepository.existsByMateria_IdAndFornecedor_Id(materiaId, info.fornecedorId()))
            throw new MateriaFornecedorException(MateriaFornecedorErrorCode.ASSOCIACAO_ALREADY_EXISTS);

        BigDecimal precoUnitarioEur = calcularPrecoEur(info.precoUnitario(), moeda.getTaxaConversaoEur());

        try
        {
            MateriaFornecedor mf = new MateriaFornecedor(
                    materia,
                    fornecedor,
                    moeda,
                    info.precoUnitario(),
                    precoUnitarioEur,
                    info.prazoEstimadoEntregaDias(),
                    info.preferencial());
            return toResponse(materiaFornecedorRepository.save(mf));
        }
        catch(Exception e)
        {
            throw new MateriaFornecedorException(MateriaFornecedorErrorCode.MATERIA_FORNECEDOR_CREATE_FAILED);
        }
    }

    public MateriaFornecedorResponse findById(UUID materiaId, UUID id)
    {
        materiaPrimaRepository.findByIdAndIsActiveIsTrue(materiaId)
                .orElseThrow(()->new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));

        return materiaFornecedorRepository.findByIdAndIsActiveTrue(id)
                .map(this::toResponse)
                .orElseThrow(()->new MateriaFornecedorException(MateriaFornecedorErrorCode.MATERIA_FORNECEDOR_NOT_FOUND));
    }

    // Devolvo todos os fornecedores disponiveis para x materia
    public Page<MateriaFornecedorResponse> findAllByMateria(UUID materiaId, Pageable pageable)
    {
        materiaPrimaRepository.findByIdAndIsActiveIsTrue(materiaId)
                .orElseThrow(()->new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));

        return materiaFornecedorRepository.findAllByMateria_IdAndIsActiveTrue(materiaId, pageable)
                .map(this::toResponse);
    }

    public Page<MateriaFornecedorResponse> findAll(Pageable pageable) {
        return materiaFornecedorRepository.findAllByIsActiveIsTrue(pageable)
                .map(this::toResponse);
    }

    @Transactional
    public MateriaFornecedorResponse updateMateriaFornecedor(UUID materiaId, UUID id, UpdateMateriaFornecedorRequest info)
    {
        materiaPrimaRepository.findByIdAndIsActiveIsTrue(materiaId)
                .orElseThrow(()->new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));

        MateriaFornecedor mf = materiaFornecedorRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(()->new MateriaFornecedorException(MateriaFornecedorErrorCode.MATERIA_FORNECEDOR_NOT_FOUND));

        Moeda moeda = moedaRepository.findByIdAndIsActiveTrue(info.moedaId())
                .orElseThrow(()->new MoedaException(MoedaErrorCode.MOEDA_NOT_FOUND));

        BigDecimal precoUnitarioEur = calcularPrecoEur(info.precoUnitario(), moeda.getTaxaConversaoEur());

        try
        {
            mf.setMoeda(moeda);
            mf.setPrecoUnitario(info.precoUnitario());
            mf.setPrecoUnitarioEur(precoUnitarioEur);
            mf.setPrazoEstimadoEntregaDias(info.prazoEstimadoEntregaDias());
            mf.setPreferencial(info.preferencial());
            return toResponse(materiaFornecedorRepository.save(mf));
        }
        catch(Exception e)
        {
            throw new MateriaFornecedorException(MateriaFornecedorErrorCode.MATERIA_FORNECEDOR_UPDATE_FAILED);
        }
    }

    @Transactional
    public void softDelete(UUID materiaId, UUID id)
    {
        materiaPrimaRepository.findByIdAndIsActiveIsTrue(materiaId)
                .orElseThrow(()->new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));

        MateriaFornecedor mf = materiaFornecedorRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(()->new MateriaFornecedorException(MateriaFornecedorErrorCode.MATERIA_FORNECEDOR_NOT_FOUND));

        mf.softDelete();
        materiaFornecedorRepository.save(mf);
    }

    private BigDecimal calcularPrecoEur(BigDecimal precoUnitario, BigDecimal taxaConversaoEur)
    {
        return precoUnitario.multiply(taxaConversaoEur).setScale(2, RoundingMode.HALF_UP);
    }

    private MateriaFornecedorResponse toResponse(MateriaFornecedor mf)
    {
        return new MateriaFornecedorResponse(
                mf.getId(),
                mf.getMateria().getId(),
                mf.getMateria().getNome(),
                mf.getFornecedor().getId(),
                mf.getFornecedor().getNome(),
                mf.getMoeda().getId(),
                mf.getMoeda().getCodigo(),
                mf.getMoeda().getSimbolo(),
                mf.getPrecoUnitario(),
                mf.getPrecoUnitarioEur(),
                mf.getPrazoEstimadoEntregaDias(),
                mf.isPreferencial(),
                mf.isActive(),
                mf.getCreatedAt(),
                mf.getUpdatedAt());
    }
}
