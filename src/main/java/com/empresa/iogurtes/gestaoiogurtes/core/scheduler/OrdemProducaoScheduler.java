package com.empresa.iogurtes.gestaoiogurtes.core.scheduler;

import org.springframework.stereotype.Component;

@Component
public class OrdemProducaoScheduler {

    /*private final OrdemProducaoRepository ordemRepository;
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
    }*/
}