package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.ordemproducao.*;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima.MateriaPrimaErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima.MateriaPrimaException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.ordemproducao.OrdemProducaoErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.ordemproducao.OrdemProducaoException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.produtofinal.ProdutoFinalErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.produtofinal.ProdutoFinalException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.user.UserErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.user.UserException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.*;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoOrdem;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoMP;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoPF;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrdemProducaoService {

    private final OrdemProducaoRepository ordemRepository;
    private final OrdemProducaoProdutoRepository ordemProdutoRepository;
    private final ConsumoProducaoRepository consumoRepository;
    private final LoteProducaoRepository loteRepository;
    private final MovimentoStockPFRepository movimentoStockPfRepository;
    private final MovimentoStockMPRepository movimentoStockMpRepository;
    private final ProdutoFinalRepository produtoFinalRepository;
    private final ProdutoMateriaRepository produtoMateriaRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;
    private final UserRepository userRepository;

    public OrdemProducaoService(OrdemProducaoRepository ordemRepository,
                                OrdemProducaoProdutoRepository ordemProdutoRepository,
                                ConsumoProducaoRepository consumoRepository,
                                LoteProducaoRepository loteRepository,
                                MovimentoStockPFRepository movimentoStockPfRepository,
                                MovimentoStockMPRepository movimentoStockMpRepository,
                                ProdutoFinalRepository produtoFinalRepository,
                                ProdutoMateriaRepository produtoMateriaRepository,
                                MateriaPrimaRepository materiaPrimaRepository,
                                UserRepository userRepository
                                ) {
        this.ordemRepository = ordemRepository;
        this.ordemProdutoRepository = ordemProdutoRepository;
        this.consumoRepository = consumoRepository;
        this.loteRepository = loteRepository;
        this.movimentoStockPfRepository = movimentoStockPfRepository;
        this.produtoFinalRepository = produtoFinalRepository;
        this.produtoMateriaRepository = produtoMateriaRepository;
        this.materiaPrimaRepository = materiaPrimaRepository;
        this.userRepository = userRepository;
        this.movimentoStockMpRepository = movimentoStockMpRepository;
    }

    @Transactional
    public OrdemProducaoResponse createOrdem(CreateOrdemProducaoRequest info) {
        User user = userRepository.findByIdAndIsActiveIsTrue(info.userId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Set<UUID> produtoIds = info.produtos().stream()
                .map(CreateOrdemProducaoProdutoRequest::produtoId)//Aqui pego nos UUIDS das materias
                .collect(Collectors.toSet()); // coloco todas num set(coleçao que nao aceita repetidas)

        if (produtoIds.size() != info.produtos().size())
            throw new OrdemProducaoException(OrdemProducaoErrorCode.PRODUTO_DUPLICADO);

        // ── Passo 1: calcular consumos totais agregados por matéria ──────────
        // Mapa: materiaId → quantidade total necessária
        Map<UUID, BigDecimal> consumosTotais = new HashMap<>();

        for (CreateOrdemProducaoProdutoRequest prodInfo : info.produtos()) {
            ProdutoFinal produto = produtoFinalRepository.findByIdAndIsActiveIsTrue(prodInfo.produtoId())
                    .orElseThrow(() -> new ProdutoFinalException(ProdutoFinalErrorCode.PRODUTO_FINAL_NOT_FOUND));

            List<ProdutoMateria> composicao = produtoMateriaRepository.findAllByProduto_IdAndIsActiveTrue(prodInfo.produtoId());

            if (composicao.isEmpty())
                throw new OrdemProducaoException(OrdemProducaoErrorCode.PRODUTO_SEM_COMPOSICAO, produto.getNome());

            for (ProdutoMateria pm : composicao) {
                BigDecimal consumo = pm.getQuantidadePorUnidadeProduto().multiply(prodInfo.quantidadeKg());
                consumosTotais.merge(pm.getMateria().getId(), consumo, BigDecimal::add);
            }
        }

        // ── Passo 2: validar stock suficiente para todas as matérias ─────────
        for (Map.Entry<UUID, BigDecimal> entry : consumosTotais.entrySet()) {
            MateriaPrima materia = materiaPrimaRepository.findByIdAndIsActiveIsTrue(entry.getKey())
                    .orElseThrow(() -> new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));

            // Aqui so verifico se o stock atual é menor que o consumo necessário
            if (materia.getStockAtual().compareTo(entry.getValue()) < 0)
                throw new OrdemProducaoException(
                        OrdemProducaoErrorCode.STOCK_INSUFICIENTE,
                        materia.getNome() + " (disponível: " + materia.getStockAtual() + ", necessário: " + entry.getValue() + ")"
                );
        }

        // ── Passo 3: descontar stock das matérias primas ─────────────────────
        for (Map.Entry<UUID, BigDecimal> entry : consumosTotais.entrySet()) {
            MateriaPrima materia = materiaPrimaRepository.findByIdAndIsActiveIsTrue(entry.getKey())
                    .orElseThrow(() -> new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));
            materia.setStockAtual(materia.getStockAtual().subtract(entry.getValue()));
            materiaPrimaRepository.save(materia);
        }

        // ── Passo 4: criar ordem ──────────────────────────────────────────────
        // Aqui deixo assim so passo user e observaçoes porque o resto está na entidade
        OrdemProducao ordem = new OrdemProducao(user, info.observacoes());
        OrdemProducao savedOrdem = ordemRepository.save(ordem);

        // ── Passo 5: criar ordem_producao_produtos ────────────────────────────
        for (CreateOrdemProducaoProdutoRequest prodInfo : info.produtos()) {
            ProdutoFinal produto = produtoFinalRepository.findByIdAndIsActiveIsTrue(prodInfo.produtoId())
                    .orElseThrow(() -> new ProdutoFinalException(ProdutoFinalErrorCode.PRODUTO_FINAL_NOT_FOUND));;
            ordemProdutoRepository.save(new OrdemProducaoProduto(savedOrdem, produto, prodInfo.quantidadeKg()));
        }

        // ── Passo 6: criar consumos_producao ─────────────────────────────────
        for (Map.Entry<UUID, BigDecimal> entry : consumosTotais.entrySet()) {
            MateriaPrima materia = materiaPrimaRepository.findByIdAndIsActiveIsTrue(entry.getKey())
                    .orElseThrow(() -> new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));;
            consumoRepository.save(new ConsumoProducao(savedOrdem, materia, entry.getValue()));
        }

        return toResponse(savedOrdem);
    }


    public OrdemProducaoResponse findById(UUID id) {
        return ordemRepository.findByIdAndIsActiveTrue(id)
                .map(this::toResponse)
                .orElseThrow(() -> new OrdemProducaoException(OrdemProducaoErrorCode.ORDEM_NOT_FOUND));
    }

    public Page<OrdemProducaoResponse> findAll(Pageable pageable) {
        return ordemRepository.findAllByIsActiveTrue(pageable).map(this::toResponse);
    }

    public Page<OrdemProducaoResponse> findByEstado(EstadoOrdem estado, Pageable pageable) {
        return ordemRepository.findAllByEstadoAndIsActiveTrue(estado, pageable).map(this::toResponse);
    }

    @Transactional
    public OrdemProducaoResponse concluir(UUID id) {
        OrdemProducao ordem = ordemRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new OrdemProducaoException(OrdemProducaoErrorCode.ORDEM_NOT_FOUND));

        if (ordem.getEstado() != EstadoOrdem.EM_PRODUCAO)
            throw new OrdemProducaoException(OrdemProducaoErrorCode.TRANSICAO_ESTADO_INVALIDA);

        List<OrdemProducaoProduto> produtos = ordemProdutoRepository.findAllByOrdem_IdAndIsActiveTrue(id);
        LocalDate hoje = LocalDate.now();

        for (OrdemProducaoProduto opp : produtos) {
            ProdutoFinal produto = opp.getProduto();

            String numeroLote = gerarNumeroLote(hoje);

            LocalDate dataValidade = produto.getValidadeDias() != null
                    ? hoje.plusDays(produto.getValidadeDias())
                    : hoje.plusDays(30); // fallback 30 dias

            LoteProducao lote = new LoteProducao(
                    ordem, produto, numeroLote,
                    opp.getQuantidadeKg(), hoje, dataValidade
            );
            LoteProducao savedLote = loteRepository.save(lote);

            // Cria movimento stock PF do tipo PRODUCAO
            movimentoStockPfRepository.save(new MovimentoStockPF(
                    savedLote,
                    ordem.getUser(),
                    TipoMovimentoPF.PRODUCAO,
                    opp.getQuantidadeKg(),
                    "Produção automática — Ordem #" + id
            ));

            movimentoStockMpRepository.save(new MovimentoStockMP(
                    ordem.getUser(),
                    TipoMovimentoMP.SAIDA,
                    "Produção automática — Ordem #" + id
            ));

            // Incrementa quantidade_lote no produto final
            produto.setQuantidadeLote(produto.getQuantidadeLote() + 1);
            produtoFinalRepository.save(produto);
        }

        ordem.setEstado(EstadoOrdem.CONCLUIDA);
        ordem.setDataFim(LocalDateTime.now());
        return toResponse(ordemRepository.save(ordem));
    }

    @Transactional
    public OrdemProducaoResponse aprovar(UUID id) {
        OrdemProducao ordem = ordemRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new OrdemProducaoException(OrdemProducaoErrorCode.ORDEM_NOT_FOUND));

        if (ordem.getEstado() != EstadoOrdem.AGUARDA_APROVACAO)
            throw new OrdemProducaoException(OrdemProducaoErrorCode.TRANSICAO_ESTADO_INVALIDA);

        List<OrdemProducaoProduto> produtos = ordemProdutoRepository.findAllByOrdem_IdAndIsActiveTrue(id);

        // ── Calcular consumos totais necessários ──────────────────────────────────
        Map<UUID, BigDecimal> consumosTotais = new HashMap<>();

        for (OrdemProducaoProduto opp : produtos) {
            List<ProdutoMateria> composicao = produtoMateriaRepository
                    .findAllByProduto_IdAndIsActiveTrue(opp.getProduto().getId());

            if (composicao.isEmpty())
                throw new OrdemProducaoException(
                        OrdemProducaoErrorCode.PRODUTO_SEM_COMPOSICAO,
                        opp.getProduto().getNome()
                );

            for (ProdutoMateria pm : composicao) {
                BigDecimal consumo = pm.getQuantidadePorUnidadeProduto().multiply(opp.getQuantidadeKg());
                consumosTotais.merge(pm.getMateria().getId(), consumo, BigDecimal::add);
            }
        }

        // ── Validar stock suficiente ──────────────────────────────────────────────
        for (Map.Entry<UUID, BigDecimal> entry : consumosTotais.entrySet()) {
            MateriaPrima materia = materiaPrimaRepository.findByIdAndIsActiveIsTrue(entry.getKey())
                    .orElseThrow(() -> new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));

            if (materia.getStockAtual().compareTo(entry.getValue()) < 0)
                throw new OrdemProducaoException(
                        OrdemProducaoErrorCode.STOCK_INSUFICIENTE,
                        materia.getNome() + " (disponível: " + materia.getStockAtual() + ", necessário: " + entry.getValue() + ")"
                );
        }

        // ── Descontar stock das matérias primas ───────────────────────────────────
        for (Map.Entry<UUID, BigDecimal> entry : consumosTotais.entrySet()) {
            MateriaPrima materia = materiaPrimaRepository.findByIdAndIsActiveIsTrue(entry.getKey())
                    .orElseThrow(() -> new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));
            materia.setStockAtual(materia.getStockAtual().subtract(entry.getValue()));
            materiaPrimaRepository.save(materia);
        }

        // ── Criar registos de consumo ─────────────────────────────────────────────
        for (Map.Entry<UUID, BigDecimal> entry : consumosTotais.entrySet()) {
            MateriaPrima materia = materiaPrimaRepository.findByIdAndIsActiveIsTrue(entry.getKey())
                    .orElseThrow(() -> new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));
            consumoRepository.save(new ConsumoProducao(ordem, materia, entry.getValue()));
        }

        ordem.setEstado(EstadoOrdem.EM_PRODUCAO);
        ordem.setAprovadoEm(LocalDateTime.now());

        return toResponse(ordemRepository.save(ordem));
    }


    @Transactional
    public OrdemProducaoResponse cancelar(UUID id) {
        OrdemProducao ordem = ordemRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new OrdemProducaoException(OrdemProducaoErrorCode.ORDEM_NOT_FOUND));

        if (ordem.getEstado() != EstadoOrdem.EM_PRODUCAO)
            throw new OrdemProducaoException(OrdemProducaoErrorCode.ORDEM_CANCEL_FAILED);

        List<ConsumoProducao> consumos = consumoRepository.findAllByOrdem_IdAndIsActiveTrue(id);
        for (ConsumoProducao consumo : consumos) {
            MateriaPrima materia = consumo.getMateria();
            materia.setStockAtual(materia.getStockAtual().add(consumo.getQuantidadeKg()));
            materiaPrimaRepository.save(materia);

            consumo.softDelete();
            consumoRepository.save(consumo);
        }

        List<OrdemProducaoProduto> produtos = ordemProdutoRepository.findAllByOrdem_IdAndIsActiveTrue(id);
        produtos.forEach(opp -> {
            opp.softDelete();
            ordemProdutoRepository.save(opp);
        });

        ordem.setEstado(EstadoOrdem.CANCELADA);
        ordem.setDataFim(LocalDateTime.now());
        return toResponse(ordemRepository.save(ordem));
    }


    private String gerarNumeroLote(LocalDate data) {
        String dataStr = data.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = loteRepository.countByDataProducao(data) + 1;
        return String.format("LOT-%s-%03d", dataStr, seq);
    }

    private OrdemProducaoResponse toResponse(OrdemProducao o) {
        List<OrdemProducaoProduto> produtos = ordemProdutoRepository.findAllByOrdem_IdAndIsActiveTrue(o.getId());
        List<ConsumoProducao> consumos = consumoRepository.findAllByOrdem_IdAndIsActiveTrue(o.getId());

        return new OrdemProducaoResponse(
                o.getId(),
                o.getUser().getId(),
                o.getUser().getNome(),
                o.getEstado(),
                o.getDataInicio(),
                o.getDataFim(),
                o.getAprovadoEm(),
                o.getObservacoes(),
                produtos.stream().map(this::toProdutoResponse).toList(),
                consumos.stream().map(this::toConsumoResponse).toList(),
                o.isActive(),
                o.getCreatedAt(),
                o.getUpdatedAt()
        );
    }

    private OrdemProducaoProdutoResponse toProdutoResponse(OrdemProducaoProduto opp) {
        return new OrdemProducaoProdutoResponse(
                opp.getId(),
                opp.getProduto().getId(),
                opp.getProduto().getNome(),
                opp.getProduto().getCodigoSku(),
                opp.getQuantidadeKg()
        );
    }

    private ConsumoProducaoResponse toConsumoResponse(ConsumoProducao c) {
        return new ConsumoProducaoResponse(
                c.getId(),
                c.getMateria().getId(),
                c.getMateria().getNome(),
                c.getMateria().getUnidade(),
                c.getQuantidadeKg()
        );
    }
}