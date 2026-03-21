package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.*;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoOrdem;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoPF;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.*;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.OrdemProducaoValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrdemProducaoService {

    private final OrdemProducaoRepository ordemRepository;
    private final ProdutoFinalRepository produtoFinalRepository;
    private final UserRepository userRepository;
    private final MovimentoStockPFService movimentoStockPFService;
    private final OrdemProducaoValidator validator;

    public OrdemProducaoService(OrdemProducaoRepository ordemRepository,
                                ProdutoFinalRepository produtoFinalRepository,
                                UserRepository userRepository,
                                MovimentoStockPFService movimentoStockPFService,
                                OrdemProducaoValidator validator) {
        this.ordemRepository = ordemRepository;
        this.produtoFinalRepository = produtoFinalRepository;
        this.userRepository = userRepository;
        this.movimentoStockPFService = movimentoStockPFService;
        this.validator = validator;
    }

    @Transactional
    public OrdemProducao createOrdem(UUID userId,
                                     LocalDateTime dataInicio, LocalDateTime dataFim,
                                     EstadoOrdem estado, String observacoes,
                                     List<OrdemProducaoProduto> produtos) {

        validator.validarCreate(userId, dataInicio, dataFim, estado, observacoes, produtos);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado"));

        OrdemProducao ordem = new OrdemProducao(user, dataInicio, dataFim, estado, observacoes);

        List<ConsumoProducao> todosConsumos = new ArrayList<>();

        for (OrdemProducaoProduto opp : produtos) {
            opp.setOrdem(ordem); // passa o id da ordem para a tabela

            ProdutoFinal produto = produtoFinalRepository.findById(opp.getProduto().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Produto não encontrado: " + opp.getProduto().getId()));

            // calcula consumos de matérias primas
            produto.getMaterias().forEach(pm -> {
                BigDecimal consumoTotal = pm.getQuantidadePorUnidadeProduto()
                        .multiply(opp.getQuantidadeKg());

                UUID materiaId = pm.getMateria().getId();

                todosConsumos.stream()
                        .filter(c -> c.getMateria().getId().equals(materiaId))
                        .findFirst()
                        .ifPresentOrElse(
                                existente -> existente.setQuantidadeKg(existente.getQuantidadeKg().add(consumoTotal)),
                                () -> todosConsumos.add(new ConsumoProducao(ordem, pm.getMateria(), consumoTotal))
                        );
            });

            // MovimentoStockPFService atualiza o stock_atual no ProdutoFinal
            movimentoStockPFService.registarMovimento(
                    produto, ordem, TipoMovimentoPF.PRODUCAO,
                    opp.getQuantidadeKg().intValue(),
                    "Produção via ordem " + ordem.getId()
            );
        }

        ordem.setProdutos(produtos);
        ordem.setConsumos(todosConsumos);

        return ordemRepository.save(ordem);
    }


    public OrdemProducao getById(UUID id) {
        return ordemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ordem não encontrada"));
    }


    public List<OrdemProducao> getAll() {
        return ordemRepository.findAll();
    }


    @Transactional
    public OrdemProducao updateOrdem(UUID id,
                                     LocalDateTime dataInicio, LocalDateTime dataFim,
                                     EstadoOrdem estado, String observacoes) {

        OrdemProducao ordem = getById(id);

        validator.validarUpdate(ordem, dataInicio, dataFim, observacoes);

        if (dataInicio != null) ordem.setDataInicio(dataInicio);
        if (dataFim != null) ordem.setDataFim(dataFim);
        if (estado != null) ordem.setEstado(estado);
        if (observacoes != null) ordem.setObservacoes(observacoes);

        return ordemRepository.save(ordem);
    }


    @Transactional
    public OrdemProducao cancelarOrdem(UUID id) {

        OrdemProducao ordem = getById(id);

        validator.validarCancelamento(ordem);

        ordem.setEstado(EstadoOrdem.CANCELADA);

        return ordemRepository.save(ordem);
    }
}