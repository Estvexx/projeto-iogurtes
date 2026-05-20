package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.moeda.CreateMoedaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.moeda.MoedaResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.moeda.UpdateMoedaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.moeda.MoedaErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.moeda.MoedaException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Moeda;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MoedaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MoedaService {

    private static final String MOEDA_BASE = "EUR";

    private final MoedaRepository moedaRepository;

    public MoedaService(MoedaRepository moedaRepository) {
        this.moedaRepository = moedaRepository;
    }

    @Transactional
    public MoedaResponse createMoeda(CreateMoedaRequest info) {
        String codigo = info.codigo().toUpperCase();

        if (moedaRepository.existsByCodigoIgnoreCase(codigo))
            throw new MoedaException(MoedaErrorCode.CODIGO_ALREADY_EXISTS);

        try {
            Moeda moeda = new Moeda(
                    codigo,
                    info.nome(),
                    info.simbolo(),
                    info.taxaConversaoEur()
            );
            return toResponse(moedaRepository.save(moeda));
        } catch (Exception e) {
            throw new MoedaException(MoedaErrorCode.MOEDA_CREATE_FAILED);
        }
    }

    public MoedaResponse findById(UUID id) {
        return moedaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new MoedaException(MoedaErrorCode.MOEDA_NOT_FOUND));
    }

    public MoedaResponse findByCodigo(String codigo) {
        return moedaRepository.findByCodigoIgnoreCase(codigo)
                .map(this::toResponse)
                .orElseThrow(() -> new MoedaException(MoedaErrorCode.MOEDA_NOT_FOUND));
    }

    public Page<MoedaResponse> findAllActive(Pageable pageable) {
        return moedaRepository.findAllByIsActiveTrue(pageable)
                .map(this::toResponse);
    }

    public Page<MoedaResponse> findAllInactive(Pageable pageable) {
        return moedaRepository.findAllByIsActiveFalse(pageable)
                .map(this::toResponse);
    }

    @Transactional
    public MoedaResponse updateMoeda(UUID id, UpdateMoedaRequest info) {
        Moeda moeda = moedaRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new MoedaException(MoedaErrorCode.MOEDA_NOT_FOUND));

        // Moeda base EUR — taxa não pode ser alterada (mantém sempre 1)
        if (MOEDA_BASE.equalsIgnoreCase(moeda.getCodigo()))
            throw new MoedaException(MoedaErrorCode.MOEDA_BASE_IMMUTABLE);

        try {
            moeda.setNome(info.nome());
            moeda.setSimbolo(info.simbolo());
            moeda.setTaxaConversaoEur(info.taxaConversaoEur());
            return toResponse(moedaRepository.save(moeda));
        } catch (Exception e) {
            throw new MoedaException(MoedaErrorCode.MOEDA_UPDATE_FAILED);
        }
    }

    @Transactional
    public void softDelete(UUID id) {
        Moeda moeda = moedaRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new MoedaException(MoedaErrorCode.MOEDA_NOT_FOUND));

        // Moeda base EUR nunca pode ser eliminada
        if (MOEDA_BASE.equalsIgnoreCase(moeda.getCodigo()))
            throw new MoedaException(MoedaErrorCode.MOEDA_BASE_IMMUTABLE);

        moeda.softDelete();
        moedaRepository.save(moeda);
    }

    private MoedaResponse toResponse(Moeda moeda) {
        return new MoedaResponse(
                moeda.getId(),
                moeda.getCodigo(),
                moeda.getNome(),
                moeda.getSimbolo(),
                moeda.getTaxaConversaoEur(),
                moeda.isActive(),
                moeda.getCreatedAt(),
                moeda.getUpdatedAt()
        );
    }
}