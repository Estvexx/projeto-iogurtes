package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.*;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomenda;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoOrdem;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoPF;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.*;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.EncomendaValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class EncomendaService {

    private final EncomendaRepository encomendaRepository;
    private final EncomendaPalletRepository encomendaPalletRepository;
    private final EncomendaOrdemRepository encomendaOrdemRepository;
    private final ProdutoFinalRepository produtoFinalRepository;
    private final UserRepository userRepository;
    private final PalletTipoRepository palletTipoRepository;
    private final OrdemProducaoRepository ordemProducaoRepository;
    private final OrdemProducaoService ordemProducaoService;
    private final MovimentoStockPFService movimentoStockPFService;
    private final EncomendaValidator validator;

    public EncomendaService(EncomendaRepository encomendaRepository,
                            EncomendaPalletRepository encomendaPalletRepository,
                            EncomendaOrdemRepository encomendaOrdemRepository,
                            ProdutoFinalRepository produtoFinalRepository,
                            UserRepository userRepository,
                            PalletTipoRepository palletTipoRepository,
                            OrdemProducaoRepository ordemProducaoRepository,
                            OrdemProducaoService ordemProducaoService,
                            MovimentoStockPFService movimentoStockPFService,
                            EncomendaValidator validator) {
        this.validator = validator;
        this.encomendaRepository = encomendaRepository;
        this.encomendaPalletRepository = encomendaPalletRepository;
        this.encomendaOrdemRepository = encomendaOrdemRepository;
        this.produtoFinalRepository = produtoFinalRepository;
        this.userRepository = userRepository;
        this.palletTipoRepository = palletTipoRepository;
        this.ordemProducaoRepository = ordemProducaoRepository;
        this.ordemProducaoService = ordemProducaoService;
        this.movimentoStockPFService = movimentoStockPFService;
    }

    // ─── CREATE ────────────────────────────────────────────────────────────────

    @Transactional
    public Encomenda createEncomenda(UUID userId, List<EncomendaPallet> pallets) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado"));

        validator.validarCreate(userId, user, pallets);
        List<EncomendaPallet> palletsMutaveis = new ArrayList<>(pallets);

        // calcula o total da encomenda
        BigDecimal totalPreco = pallets.stream()
                .map(p -> p.getPrecoPorPallet().multiply(BigDecimal.valueOf(p.getQuantidadePallets())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Encomenda encomenda = new Encomenda(user, totalPreco);
        Encomenda savedEncomenda = encomendaRepository.save(encomenda);


        boolean todosComStock = true;

        for (EncomendaPallet ep : palletsMutaveis) {
            ep.setEncomenda(savedEncomenda);

            ProdutoFinal produto = produtoFinalRepository.findById(ep.getProduto().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Produto não encontrado: " + ep.getProduto().getId()));

            PalletTipo palletTipo = palletTipoRepository.findById(ep.getPalletTipo().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Tipo de pallet não encontrado: " + ep.getPalletTipo().getId()));

            // quantidade necessária em kg = nº pallets × capacidade kg do pallet
            BigDecimal kgNecessarios = palletTipo.getCapacidadeKg()
                    .multiply(BigDecimal.valueOf(ep.getQuantidadePallets()));

            if (produto.getStockAtual() >= kgNecessarios.intValue()) {
                // ─── stock suficiente → desconta via movimento EXPEDICAO ───────
                movimentoStockPFService.registarMovimento(
                        produto, null, TipoMovimentoPF.EXPEDICAO,
                        kgNecessarios.intValue(),
                        "Expedição via encomenda " + savedEncomenda.getId()
                );
            } else {
                // ─── stock insuficiente → cria ordem de produção para aprovação ─
                todosComStock = false;

                OrdemProducao ordemPendente = new OrdemProducao(
                        user, LocalDateTime.now(), LocalDateTime.now().plusHours(8), observacoes(savedEncomenda)
                );
                ordemPendente.setEstado(EstadoOrdem.AGUARDA_APROVACAO);

                OrdemProducao savedOrdem = ordemProducaoRepository.save(ordemPendente);

                OrdemProducaoProduto opp = new OrdemProducaoProduto(
                        savedOrdem, produto.getId(), kgNecessarios
                );
                savedOrdem.setProdutos(new ArrayList<>(List.of(opp)));
                ordemProducaoRepository.save(savedOrdem);

                // liga a encomenda pallet à ordem criada
                EncomendaOrdem encomendaOrdem = new EncomendaOrdem(
                        savedOrdem, ep, ep.getQuantidadePallets()
                );
                ep.setOrdens(new ArrayList<>(List.of(encomendaOrdem)));
            }
        }

        savedEncomenda.setPallets(palletsMutaveis);
        savedEncomenda.setEstado(todosComStock ? EstadoEncomenda.confirmada : EstadoEncomenda.pendente);

        return encomendaRepository.save(savedEncomenda);
    }

    public Encomenda getById(UUID id) {
        return encomendaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Encomenda não encontrada"));
    }


    public List<Encomenda> getAll() {
        return encomendaRepository.findAllByIsActiveTrue();
    }

    public List<Encomenda> getAllIncludingInactive() {
        return encomendaRepository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        Encomenda encomenda = getById(id);
        Set<UUID> ordensParaDesativar = new HashSet<>();

        encomendaPalletRepository.findByEncomendaId(id)
                .forEach(pallet -> {
                    encomendaOrdemRepository.findByEncomendaPalletId(pallet.getId())
                            .forEach(eo -> {
                                if (eo.getOrdem() != null && eo.getOrdem().getId() != null) {
                                    ordensParaDesativar.add(eo.getOrdem().getId());
                                }
                                eo.softDelete();
                                encomendaOrdemRepository.save(eo);
                            });
                    pallet.softDelete();
                    encomendaPalletRepository.save(pallet);
                });

        ordensParaDesativar.forEach(ordemProducaoService::delete);

        encomenda.softDelete();
        encomendaRepository.save(encomenda);
    }


    private String observacoes(Encomenda encomenda) {
        return "Ordem gerada automaticamente para encomenda " + encomenda.getId();
    }
}