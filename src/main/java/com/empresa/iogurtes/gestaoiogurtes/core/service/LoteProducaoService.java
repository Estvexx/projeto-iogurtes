package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.lotes.LoteProducaoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.lote_producao.LoteProducaoErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.lote_producao.LoteProducaoException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.LoteProducao;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoteProducaoService {

    private final LoteProducaoRepository loteProducaoRepository;

    public LoteProducaoService(LoteProducaoRepository loteProducaoRepository)
    {
        this.loteProducaoRepository = loteProducaoRepository;
    }
    public LoteProducaoResponse findById(UUID id) {
        LoteProducao lote = loteProducaoRepository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new LoteProducaoException(LoteProducaoErrorCode.LOTE_NOT_FOUND));
        return toResponse(lote);
    }

    public Page<LoteProducaoResponse> findAll(Pageable pageable) {
        return loteProducaoRepository.findAllByIsActiveTrue(pageable)
                .map(this::toResponse);
    }

    private LoteProducaoResponse toResponse(LoteProducao lote) {
        return new LoteProducaoResponse(
                lote.getId(),
                lote.getOrdem().getId(),
                lote.getProduto().getId(),
                lote.getProduto().getNome(),
                lote.getNumeroLote(),
                lote.getQuantidadeKg(),
                lote.getStockAtualKg(),
                lote.getEstado().name(),
                lote.getDataProducao(),
                lote.getDataValidade(),
                lote.isActive(),
                lote.getCreatedAt()
        );
    }
}