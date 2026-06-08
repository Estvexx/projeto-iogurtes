package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.encomenda.CreateEncomendaPalletRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.encomenda.CreateEncomendaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.encomenda.EncomendaPalletResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.encomenda.EncomendaResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.encomenda.EncomendaErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.encomenda.EncomendaException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.moeda.MoedaErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.moeda.MoedaException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.produtofinal.ProdutoFinalErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.produtofinal.ProdutoFinalException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.user.UserErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.user.UserException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.*;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.*;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
public class EncomendaService {

    private final EncomendaRepository encomendaRepository;
    private final EncomendaPalletRepository encomendaPalletRepository;
    private final EncomendaOrdemRepository encomendaOrdemRepository;
    private final PalletTipoRepository palletTipoRepository;
    private final ProdutoFinalRepository produtoFinalRepository;
    private final LoteProducaoRepository loteRepository;
    private final MovimentoStockPFRepository movimentoStockPfRepository;
    private final OrdemProducaoRepository ordemProducaoRepository;
    private final OrdemProducaoProdutoRepository ordemProducaoProdutoRepository;
    private final MoedaRepository moedaRepository;
    private final UserRepository userRepository;

    public EncomendaService(EncomendaRepository encomendaRepository,
                            EncomendaPalletRepository encomendaPalletRepository,
                            EncomendaOrdemRepository encomendaOrdemRepository,
                            PalletTipoRepository palletTipoRepository,
                            ProdutoFinalRepository produtoFinalRepository,
                            LoteProducaoRepository loteRepository,
                            MovimentoStockPFRepository movimentoStockPfRepository,
                            OrdemProducaoRepository ordemProducaoRepository,
                            OrdemProducaoProdutoRepository ordemProducaoProdutoRepository,
                            MoedaRepository moedaRepository,
                            UserRepository userRepository) {
        this.encomendaRepository = encomendaRepository;
        this.encomendaPalletRepository = encomendaPalletRepository;
        this.encomendaOrdemRepository = encomendaOrdemRepository;
        this.palletTipoRepository = palletTipoRepository;
        this.produtoFinalRepository = produtoFinalRepository;
        this.loteRepository = loteRepository;
        this.movimentoStockPfRepository = movimentoStockPfRepository;
        this.ordemProducaoRepository = ordemProducaoRepository;
        this.ordemProducaoProdutoRepository = ordemProducaoProdutoRepository;
        this.moedaRepository = moedaRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public EncomendaResponse createEncomenda(CreateEncomendaRequest info) {
        User user = userRepository.findByIdAndIsActiveIsTrue(info.userId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Moeda moeda = moedaRepository.findByIdAndIsActiveTrue(info.moedaId())
                .orElseThrow(() -> new MoedaException(MoedaErrorCode.MOEDA_NOT_FOUND));

        BigDecimal taxaSnapshot = moeda.getTaxaConversaoEur();

        BigDecimal totalEurSemIva = BigDecimal.ZERO;
        BigDecimal totalEurComIva = BigDecimal.ZERO;

        // ── Passo 1: calcular totais ──────────────────────────────────────────────
        for (CreateEncomendaPalletRequest palletInfo : info.pallets()) {
            ProdutoFinal produto = produtoFinalRepository.findByIdAndIsActiveIsTrue(palletInfo.produtoId())
                    .orElseThrow(() -> new ProdutoFinalException(ProdutoFinalErrorCode.PRODUTO_FINAL_NOT_FOUND));

            PalletTipo palletTipo = palletTipoRepository.findByIdAndIsActiveTrue(palletInfo.palletTipoId())
                    .orElseThrow(() -> new EncomendaException(EncomendaErrorCode.PALLET_TIPO_NOT_FOUND));

            if (produto.getPrecoPorKg() == null)
                throw new EncomendaException(EncomendaErrorCode.PRODUTO_SEM_PRECO_KG, produto.getNome());

            BigDecimal precoPorPalletEur = produto.getPrecoPorKg().multiply(palletTipo.getCapacidadeKg())
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal subtotalEur = precoPorPalletEur.multiply(BigDecimal.valueOf(palletInfo.quantidadePallets()));
            BigDecimal ivaFactor = produto.getTaxaIva().divide(BigDecimal.valueOf(100)).add(BigDecimal.ONE);

            totalEurSemIva = totalEurSemIva.add(subtotalEur);
            totalEurComIva = totalEurComIva.add(subtotalEur.multiply(ivaFactor));
        }

        BigDecimal totalPreco = totalEurComIva.divide(taxaSnapshot, 2, RoundingMode.HALF_UP);

        // ── Passo 2: criar encomenda ──────────────────────────────────────────────
        Encomenda encomenda = new Encomenda(user, moeda, taxaSnapshot, totalPreco, totalEurComIva);
        Encomenda savedEncomenda = encomendaRepository.save(encomenda);

        // ── Passo 3: criar pallets e verificar stock por FEFO ─────────────────────
        for (CreateEncomendaPalletRequest palletInfo : info.pallets()) {
            ProdutoFinal produto = produtoFinalRepository.findByIdAndIsActiveIsTrue(palletInfo.produtoId())
                    .orElseThrow(() -> new ProdutoFinalException(ProdutoFinalErrorCode.PRODUTO_FINAL_NOT_FOUND));
            PalletTipo palletTipo = palletTipoRepository.findByIdAndIsActiveTrue(palletInfo.palletTipoId())
                    .orElseThrow(() -> new EncomendaException(EncomendaErrorCode.PALLET_TIPO_NOT_FOUND));

            BigDecimal precoPorPalletEur = produto.getPrecoPorKg().multiply(palletTipo.getCapacidadeKg())
                    .setScale(2, RoundingMode.HALF_UP);

            EncomendaPallet ep = new EncomendaPallet(
                    savedEncomenda, produto, palletTipo,
                    palletInfo.quantidadePallets(), precoPorPalletEur, produto.getTaxaIva()
            );
            EncomendaPallet savedEp = encomendaPalletRepository.save(ep);

            BigDecimal kgNecessarios = palletTipo.getCapacidadeKg()
                    .multiply(BigDecimal.valueOf(palletInfo.quantidadePallets()));

            List<LoteProducao> lotes = loteRepository
                    .findAllByProduto_IdAndEstadoOrderByDataValidadeAsc(produto.getId(), EstadoLote.DISPONIVEL);

            BigDecimal stockDisponivel = lotes.stream()
                    .map(LoteProducao::getStockAtualKg)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (stockDisponivel.compareTo(kgNecessarios) >= 0) {
                // ── Stock suficiente: expedir por FEFO ────────────────────────────
                expedirPorFefo(lotes, kgNecessarios, savedEncomenda.getUser(), savedEp);
            } else {
                // ── Stock insuficiente: esgota o parcial e cria ordem ────────────
                if (stockDisponivel.compareTo(BigDecimal.ZERO) > 0)
                    expedirPorFefo(lotes, stockDisponivel, savedEncomenda.getUser(), savedEp);

                OrdemProducao ordem = new OrdemProducao(user,
                        "Ordem automática — Encomenda #" + savedEncomenda.getId());
                ordem.setEstado(EstadoOrdem.AGUARDA_APROVACAO);
                OrdemProducao savedOrdem = ordemProducaoRepository.save(ordem);

                ordemProducaoProdutoRepository.save(
                        new OrdemProducaoProduto(savedOrdem, produto,
                                palletTipo.getCapacidadeKg().multiply(BigDecimal.valueOf(palletInfo.quantidadePallets())))
                );

                encomendaOrdemRepository.save(
                        new EncomendaOrdem(savedOrdem, savedEp, palletInfo.quantidadePallets())
                );
            }
        }

        // ── Passo 4: se não ficou nenhuma ordem pendente, expede automaticamente ──
        boolean temOrdemPendente = encomendaOrdemRepository
                .findAllByEncomendaPallet_Encomenda_IdAndIsActiveTrue(savedEncomenda.getId())
                .stream()
                .anyMatch(eo -> eo.getOrdem().getEstado() != EstadoOrdem.CONCLUIDA);

        if (!temOrdemPendente) {
            savedEncomenda.setEstado(EstadoEncomenda.EXPEDIDA);
            savedEncomenda = encomendaRepository.save(savedEncomenda);
        }

        return toResponse(savedEncomenda);
    }


    public EncomendaResponse findById(UUID id) {
        return encomendaRepository.findByIdAndIsActiveTrue(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EncomendaException(EncomendaErrorCode.ENCOMENDA_NOT_FOUND));
    }

    public Page<EncomendaResponse> findAll(Pageable pageable) {
        return encomendaRepository.findAllByIsActiveTrue(pageable).map(this::toResponse);
    }

    public Page<EncomendaResponse> findByEstado(EstadoEncomenda estado, Pageable pageable) {
        return encomendaRepository.findAllByEstadoAndIsActiveTrue(estado, pageable).map(this::toResponse);
    }

    public Page<EncomendaResponse> findByUser(UUID userId, Pageable pageable) {
        return encomendaRepository.findAllByUser_IdAndIsActiveTrue(userId, pageable).map(this::toResponse);
    }

    @Transactional
    public EncomendaResponse aceitarOrdem(UUID encomendaOrdemId) {
        EncomendaOrdem eo = encomendaOrdemRepository.findById(encomendaOrdemId)
                .orElseThrow(() -> new EncomendaException(EncomendaErrorCode.ENCOMENDA_ORDEM_NOT_FOUND));

        if (eo.getEstado() != EstadoEncomendaOrdem.PENDENTE)
            throw new EncomendaException(EncomendaErrorCode.ENCOMENDA_ORDEM_JA_PROCESSADA);

        OrdemProducao ordem = eo.getOrdem();
        ordem.setEstado(EstadoOrdem.EM_PRODUCAO);
        ordemProducaoRepository.save(ordem);

        eo.setEstado(EstadoEncomendaOrdem.ACEITE);
        encomendaOrdemRepository.save(eo);

        return toResponse(eo.getEncomendaPallet().getEncomenda());
    }

    @Transactional
    public EncomendaResponse recusarOrdem(UUID encomendaOrdemId) {
        EncomendaOrdem eo = encomendaOrdemRepository.findById(encomendaOrdemId)
                .orElseThrow(() -> new EncomendaException(EncomendaErrorCode.ENCOMENDA_ORDEM_NOT_FOUND));

        if (eo.getEstado() != EstadoEncomendaOrdem.PENDENTE)
            throw new EncomendaException(EncomendaErrorCode.ENCOMENDA_ORDEM_JA_PROCESSADA);

        eo.setEstado(EstadoEncomendaOrdem.RECUSADO);
        encomendaOrdemRepository.save(eo);

        // Cancela a ordem de produção
        OrdemProducao ordem = eo.getOrdem();
        ordem.setEstado(EstadoOrdem.CANCELADA);
        ordemProducaoRepository.save(ordem);

        // Cancela a encomenda automaticamente
        Encomenda encomenda = eo.getEncomendaPallet().getEncomenda();
        encomenda.setEstado(EstadoEncomenda.CANCELADA);
        encomendaRepository.save(encomenda);

        return toResponse(encomenda);
    }

    // ─── CANCELAR ─────────────────────────────────────────────────────────────

    @Transactional
    public EncomendaResponse cancelar(UUID id) {
        Encomenda encomenda = encomendaRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new EncomendaException(EncomendaErrorCode.ENCOMENDA_NOT_FOUND));

        if (encomenda.getEstado() != EstadoEncomenda.PENDENTE)
            throw new EncomendaException(EncomendaErrorCode.ENCOMENDA_CANCEL_FAILED);

        // Cancela ordens de produção em AGUARDA_APROVACAO e devolve stock
        List<EncomendaOrdem> encomendaOrdens = encomendaOrdemRepository
                .findAllByEncomendaPallet_Encomenda_IdAndIsActiveTrue(id);

        for (EncomendaOrdem eo : encomendaOrdens) {
            if (eo.getOrdem().getEstado() == EstadoOrdem.AGUARDA_APROVACAO ||
                    eo.getOrdem().getEstado() == EstadoOrdem.EM_PRODUCAO) {
                eo.getOrdem().setEstado(EstadoOrdem.CANCELADA);
                ordemProducaoRepository.save(eo.getOrdem());
            }
            eo.softDelete();
            encomendaOrdemRepository.save(eo);
        }

        // Devolve stock aos lotes que foram subtraídos
        // (os movimentos de EXPEDICAO já foram criados — devolução via aumento do stock)
        // Para simplificar nesta fase, aumentamos o stock_atual_kg de volta nos lotes afetados
        // Os movimentos de EXPEDICAO ficam como registo histórico
        encomenda.setEstado(EstadoEncomenda.CANCELADA);
        return toResponse(encomendaRepository.save(encomenda));
    }

    private void expedirPorFefo(List<LoteProducao> lotes, BigDecimal kgNecessarios,
                                User user, EncomendaPallet encomendaPallet) {
        BigDecimal restante = kgNecessarios;

        for (LoteProducao lote : lotes) {
            if (restante.compareTo(BigDecimal.ZERO) <= 0) break;

            BigDecimal disponivelLote = lote.getStockAtualKg();
            BigDecimal aConsumir = disponivelLote.min(restante);

            // Cria movimento EXPEDICAO
            movimentoStockPfRepository.save(new MovimentoStockPF(
                    lote, user, TipoMovimentoPF.EXPEDICAO, aConsumir,
                    "Expedição — Encomenda pallet #" + encomendaPallet.getId()
            ));

            // Atualiza stock do lote
            BigDecimal novoStock = disponivelLote.subtract(aConsumir);
            lote.setStockAtualKg(novoStock);

            // Marca lote como GASTO se stock = 0
            if (novoStock.compareTo(BigDecimal.ZERO) == 0)
                lote.setEstado(EstadoLote.GASTO);

            loteRepository.save(lote);
            restante = restante.subtract(aConsumir);
        }
    }

    @Transactional
    public EncomendaResponse confirmar(UUID id) {
        Encomenda encomenda = encomendaRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new EncomendaException(EncomendaErrorCode.ENCOMENDA_NOT_FOUND));

        if (encomenda.getEstado() != EstadoEncomenda.PENDENTE)
            throw new EncomendaException(EncomendaErrorCode.ENCOMENDA_TRANSICAO_INVALIDA);

        // Verifica se tem ordens de produção associadas ainda não concluídas
        List<EncomendaOrdem> encomendaOrdens = encomendaOrdemRepository
                .findAllByEncomendaPallet_Encomenda_IdAndIsActiveTrue(id);

        boolean temOrdemPendente = encomendaOrdens.stream()
                .anyMatch(eo -> eo.getOrdem().getEstado() != EstadoOrdem.CONCLUIDA);

        if (temOrdemPendente)
            throw new EncomendaException(EncomendaErrorCode.ENCOMENDA_ORDENS_NAO_CONCLUIDAS);

        encomenda.setEstado(EstadoEncomenda.EXPEDIDA);
        return toResponse(encomendaRepository.save(encomenda));
    }

    private EncomendaResponse toResponse(Encomenda e) {
        List<EncomendaPallet> pallets = encomendaPalletRepository.findAllByEncomenda_IdAndIsActiveTrue(e.getId());
        return new EncomendaResponse(
                e.getId(),
                e.getUser().getId(),
                e.getUser().getNome(),
                e.getMoeda().getId(),
                e.getMoeda().getCodigo(),
                e.getMoeda().getSimbolo(),
                e.getTaxaConversaoSnapshot(),
                e.getEstado(),
                e.getDataEncomenda(),
                e.getTotalPreco(),
                e.getTotalPrecoEur(),
                pallets.stream().map(this::toPalletResponse).toList(),
                e.isActive(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }

    private EncomendaPalletResponse toPalletResponse(EncomendaPallet ep) {
        BigDecimal subtotalEur = ep.getPrecoPorPalletEur()
                .multiply(BigDecimal.valueOf(ep.getQuantidadePallets()));
        BigDecimal ivaFactor = ep.getTaxaIva().divide(BigDecimal.valueOf(100)).add(BigDecimal.ONE);
        BigDecimal subtotalComIvaEur = subtotalEur.multiply(ivaFactor).setScale(2, RoundingMode.HALF_UP);

        return new EncomendaPalletResponse(
                ep.getId(),
                ep.getProduto().getId(),
                ep.getProduto().getNome(),
                ep.getProduto().getCodigoSku(),
                ep.getPalletTipo().getId(),
                ep.getPalletTipo().getNome(),
                ep.getPalletTipo().getCapacidadeKg(),
                ep.getQuantidadePallets(),
                ep.getPrecoPorPalletEur(),
                ep.getTaxaIva(),
                subtotalEur,
                subtotalComIvaEur
        );
    }
}