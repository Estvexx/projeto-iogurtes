package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.*;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoOrdem;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoMP;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.EncomendaOrdemRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MovimentoStockPFRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.OrdemProducaoRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.ProdutoFinalRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
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
    private final MovimentoStockMPService movimentoStockMPService;
    private final EncomendaOrdemRepository encomendaOrdemRepository;
    private final MovimentoStockPFRepository movimentoStockPFRepository;
    private final OrdemProducaoValidator validator;

    public OrdemProducaoService(OrdemProducaoRepository ordemRepository,
                                ProdutoFinalRepository produtoFinalRepository,
                                UserRepository userRepository,
                                MovimentoStockPFService movimentoStockPFService,
                                OrdemProducaoValidator validator,
                                MovimentoStockMPService movimentoStockMPService,
                                EncomendaOrdemRepository encomendaOrdemRepository,
                                MovimentoStockPFRepository movimentoStockPFRepository) {
        this.ordemRepository = ordemRepository;
        this.produtoFinalRepository = produtoFinalRepository;
        this.userRepository = userRepository;
        this.movimentoStockPFService = movimentoStockPFService;
        this.movimentoStockMPService = movimentoStockMPService;
        this.encomendaOrdemRepository = encomendaOrdemRepository;
        this.movimentoStockPFRepository = movimentoStockPFRepository;
        this.validator = validator;
    }

    @Transactional
    public OrdemProducao createOrdem(UUID userId,
                                     LocalDateTime dataInicio, LocalDateTime dataFim,
                                      String observacoes,
                                     List<OrdemProducaoProduto> produtos) {

        validator.validarCreate(userId, dataInicio, dataFim, observacoes, produtos);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado"));

        OrdemProducao ordem = new OrdemProducao(user, dataInicio, dataFim, observacoes);
        OrdemProducao savedOrdem = ordemRepository.save(ordem);

        List<ConsumoProducao> todosConsumos = new ArrayList<>();
        List<OrdemProducaoProduto> produtosMutaveis = new ArrayList<>(produtos);

        for (OrdemProducaoProduto opp : produtosMutaveis) {
            opp.setOrdem(savedOrdem);

            ProdutoFinal produto = produtoFinalRepository.findById(opp.getProduto().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Produto não encontrado: " + opp.getProduto().getId()));

            produto.getMaterias().forEach(pm -> {
                BigDecimal consumoTotal = pm.getQuantidadePorUnidadeProduto()
                        .multiply(opp.getQuantidadeKg());

                UUID materiaId = pm.getMateria().getId();

                todosConsumos.stream()
                        .filter(c -> c.getMateria().getId().equals(materiaId))
                        .findFirst()
                        .ifPresentOrElse(
                                existente -> existente.setQuantidadeKg(existente.getQuantidadeKg().add(consumoTotal)),
                                () -> todosConsumos.add(new ConsumoProducao(savedOrdem, pm.getMateria(), consumoTotal))
                        );

                movimentoStockMPService.registarMovimento(
                        userId, materiaId, TipoMovimentoMP.SAIDA, consumoTotal,
                        "Consumo para produção via ordem " + savedOrdem.getId()
                );
            });
        }

        savedOrdem.setProdutos(produtosMutaveis);
        savedOrdem.setConsumos(todosConsumos);

        return ordemRepository.save(savedOrdem);
    }


    public OrdemProducao getById(UUID id) {
        return ordemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ordem não encontrada"));
    }


    public List<OrdemProducao> getAll() {
        return ordemRepository.findAllByIsActiveTrue();
    }

    public List<OrdemProducao> getAllIncludingInactive() {
        return ordemRepository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        OrdemProducao ordem = getById(id);

        ordem.getProdutos().forEach(produto -> produto.softDelete());
        ordem.getConsumos().forEach(consumo -> consumo.softDelete());

        encomendaOrdemRepository.findByOrdemId(id)
                .forEach(eo -> {
                    eo.softDelete();
                    encomendaOrdemRepository.save(eo);
                });

        movimentoStockPFRepository.findByOrdemId(id)
                .forEach(movimento -> {
                    movimento.softDelete();
                    movimentoStockPFRepository.save(movimento);
                });

        ordem.softDelete();
        ordemRepository.save(ordem);
    }


    @Transactional
    public OrdemProducao updateOrdem(UUID id,
                                     LocalDateTime dataInicio, LocalDateTime dataFim,
                                     String observacoes) {

        OrdemProducao ordem = getById(id);

        validator.validarUpdate(ordem, dataInicio, dataFim, observacoes);

        if (dataInicio != null) ordem.setDataInicio(dataInicio);
        if (dataFim != null) ordem.setDataFim(dataFim);
        if (observacoes != null) ordem.setObservacoes(observacoes);

        return ordemRepository.save(ordem);
    }


    @Transactional
    public OrdemProducao cancelarOrdem(UUID id, UUID userId) {

        OrdemProducao ordem = getById(id);

        validator.validarCancelamento(ordem);

        // reverte os consumos de matérias primas
        for (ConsumoProducao consumo : ordem.getConsumos()) {
            movimentoStockMPService.registarMovimento(
                    userId,
                    consumo.getMateria().getId(),
                    TipoMovimentoMP.ENTRADA,
                    consumo.getQuantidadeKg(),
                    "Reversão por cancelamento da ordem " + ordem.getId()
            );
        }

        ordem.setEstado(EstadoOrdem.CANCELADA);
        return ordemRepository.save(ordem);
    }

    @Transactional
    public OrdemProducao aprovarOrdem(UUID ordemId) {
        OrdemProducao ordem = getById(ordemId);

        if (ordem.getEstado() != EstadoOrdem.AGUARDA_APROVACAO)
            throw new IllegalStateException("Ordem não está em estado de aprovação");

        List<ConsumoProducao> todosConsumos = new ArrayList<>();

        for (OrdemProducaoProduto opp : ordem.getProdutos()) {
            ProdutoFinal produto = produtoFinalRepository.findById(opp.getProduto().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Produto não encontrado: " + opp.getProduto().getId()));

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

                movimentoStockMPService.registarMovimento(
                        ordem.getUser().getId(), materiaId, TipoMovimentoMP.SAIDA, consumoTotal,
                        "Consumo para produção via ordem " + ordem.getId()
                );
            });
        }

        ordem.setConsumos(todosConsumos);
        ordem.setEstado(EstadoOrdem.EM_PRODUCAO);
        ordem.setAprovadoEm(LocalDateTime.now());

        return ordemRepository.save(ordem);
    }
}