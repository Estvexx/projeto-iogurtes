package com.empresa.iogurtes.gestaoiogurtes.core.scheduler;

import com.empresa.iogurtes.gestaoiogurtes.core.model.OrdemProducao;
import com.empresa.iogurtes.gestaoiogurtes.core.model.OrdemProducaoProduto;
import com.empresa.iogurtes.gestaoiogurtes.core.model.ProdutoFinal;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoOrdem;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoPF;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.OrdemProducaoRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.ProdutoFinalRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.service.MovimentoStockPFService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrdemProducaoScheduler {

    private final OrdemProducaoRepository ordemRepository;
    private final ProdutoFinalRepository produtoFinalRepository;
    private final MovimentoStockPFService movimentoStockPFService;

    public OrdemProducaoScheduler(OrdemProducaoRepository ordemRepository,
                                  ProdutoFinalRepository produtoFinalRepository,
                                  MovimentoStockPFService movimentoStockPFService) {
        this.ordemRepository = ordemRepository;
        this.produtoFinalRepository = produtoFinalRepository;
        this.movimentoStockPFService = movimentoStockPFService;
    }

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void processarOrdensFinalizadas() {
        List<OrdemProducao> ordens = ordemRepository
                .findByDataFimBeforeAndEstadoNotIn(
                        LocalDateTime.now(),
                        List.of(EstadoOrdem.CONCLUIDA, EstadoOrdem.CANCELADA)
                );

        for (OrdemProducao ordem : ordens) {
            for (OrdemProducaoProduto opp : ordem.getProdutos()) {
                ProdutoFinal produto = produtoFinalRepository.findById(opp.getProduto().getId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Produto não encontrado: " + opp.getProduto().getId()));

                movimentoStockPFService.registarMovimento(
                        produto, ordem, TipoMovimentoPF.PRODUCAO,
                        opp.getQuantidadeKg().intValue(),
                        "Produção via ordem " + ordem.getId()
                );
            }

            ordem.setEstado(EstadoOrdem.CONCLUIDA);
            ordemRepository.save(ordem);
        }
    }
}