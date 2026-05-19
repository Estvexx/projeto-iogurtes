package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.encomendamp.CreateEncomendaMPLinhaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.encomendamp.CreateEncomendaMPRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.encomendamp.EncomendaMPLinhaResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.encomendamp.EncomendaMPResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.encomenda_mp.EncomendaMPErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.encomenda_mp.EncomendaMPException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.FornecedorErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.fornecedor.FornecedorException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima.MateriaPrimaErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima.MateriaPrimaException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.moeda.MoedaErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.moeda.MoedaException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.user.UserErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.user.UserException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.*;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomendaMP;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoMP;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EncomendaMPService {

    private final EncomendaMPRepository encomendaMPRepository;
    private final EncomendaMPLinhaRepository encomendaMPLinhaRepository;
    private final MovimentoStockMPRepository movimentoStockMPRepository;
    private final MovimentoStockMPMateriaRepository movimentoStockMPMateriaRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;
    private final MateriaFornecedorRepository materiaFornecedorRepository;
    private final FornecedorRepository fornecedorRepository;
    private final MoedaRepository moedaRepository;
    private final UserRepository userRepository;

    public EncomendaMPService(EncomendaMPRepository encomendaMPRepository,
                              EncomendaMPLinhaRepository encomendaMPLinhaRepository,
                              MovimentoStockMPRepository movimentoStockMPRepository,
                              MovimentoStockMPMateriaRepository movimentoStockMPMateriaRepository,
                              MateriaPrimaRepository materiaPrimaRepository,
                              MateriaFornecedorRepository materiaFornecedorRepository,
                              FornecedorRepository fornecedorRepository,
                              MoedaRepository moedaRepository,
                              UserRepository userRepository) {
        this.encomendaMPRepository = encomendaMPRepository;
        this.encomendaMPLinhaRepository = encomendaMPLinhaRepository;
        this.movimentoStockMPRepository = movimentoStockMPRepository;
        this.movimentoStockMPMateriaRepository = movimentoStockMPMateriaRepository;
        this.materiaPrimaRepository = materiaPrimaRepository;
        this.materiaFornecedorRepository = materiaFornecedorRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.moedaRepository = moedaRepository;
        this.userRepository = userRepository;
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────

    @Transactional
    public EncomendaMPResponse createEncomenda(CreateEncomendaMPRequest info) {
        User user = userRepository.findByIdAndIsActiveIsTrue(info.userId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Fornecedor fornecedor = fornecedorRepository.findByIdAndIsActiveIsTrue(info.fornecedorId())
                .orElseThrow(() -> new FornecedorException(FornecedorErrorCode.FORNECEDOR_NOT_FOUND));

        Moeda moeda = moedaRepository.findByIdAndIsActiveTrue(info.moedaId())
                .orElseThrow(() -> new MoedaException(MoedaErrorCode.MOEDA_NOT_FOUND));

        BigDecimal taxaSnapshot = moeda.getTaxaConversaoEur();

        List<EncomendaMPLinha> linhas = new ArrayList<>();
        int maxPrazo = 0;
        BigDecimal taxaIva = BigDecimal.ZERO; // vai ser sobrescrito

        for (CreateEncomendaMPLinhaRequest linhaInfo : info.linhas()) {
            MateriaPrima materia = materiaPrimaRepository.findByIdAndIsActiveIsTrue(linhaInfo.materiaId())
                    .orElseThrow(() -> new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));

            MateriaFornecedor materiaFornecedor = materiaFornecedorRepository
                    .findByMateria_IdAndFornecedor_IdAndIsActiveIsTrue(linhaInfo.materiaId(), info.fornecedorId())
                    .orElseThrow(() -> new EncomendaMPException(EncomendaMPErrorCode.MATERIA_NAO_FORNECIDA));

            BigDecimal precoUnitario = materiaFornecedor.getPrecoUnitario();
            BigDecimal precoUnitarioEur = materiaFornecedor.getPrecoUnitarioEur();
            taxaIva = materia.getTaxaIva(); // usa a taxa da matéria-prima

            linhas.add(new EncomendaMPLinha(null, materia, linhaInfo.quantidade(),
                    precoUnitario, precoUnitarioEur, taxaIva));

            if (materiaFornecedor.getPrazoEstimadoEntregaDias() != null
                    && materiaFornecedor.getPrazoEstimadoEntregaDias() > maxPrazo) {
                maxPrazo = materiaFornecedor.getPrazoEstimadoEntregaDias();
            }
        }

        BigDecimal totalPrecoSemIva = calcularTotalSemIva(linhas, false);
        BigDecimal totalPrecoEurSemIva = calcularTotalSemIva(linhas, true);

        BigDecimal taxa = taxaIva.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP); //  transformo em 0.xx
        BigDecimal fator = BigDecimal.ONE.add(taxa); // adiciono 1 = 1.xx
        BigDecimal totalPrecoEurComIva = totalPrecoEurSemIva.multiply(fator)
                .setScale(2, RoundingMode.HALF_UP); // Arredondo a duas casas decimais

        LocalDate dataEntregaPrevista = maxPrazo > 0
                ? LocalDate.now().plusDays(maxPrazo)
                : null;

        try {
            EncomendaMP encomenda = new EncomendaMP(
                    user, fornecedor, moeda, taxaSnapshot,
                    dataEntregaPrevista, totalPrecoSemIva, totalPrecoEurSemIva,
                    totalPrecoEurComIva, info.observacoes()
            );
            EncomendaMP saved = encomendaMPRepository.save(encomenda);

            for (EncomendaMPLinha linha : linhas) {
                linha.setEncomenda(saved);
                encomendaMPLinhaRepository.save(linha);
            }

            return toResponse(saved);
        } catch (Exception e) {
            throw new EncomendaMPException(EncomendaMPErrorCode.ENCOMENDA_MP_NOT_FOUND);
        }
    }


    public EncomendaMPResponse findById(UUID id) {
        EncomendaMP encomenda = encomendaMPRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new EncomendaMPException(EncomendaMPErrorCode.ENCOMENDA_MP_NOT_FOUND));
        return toResponse(encomenda);
    }

    public Page<EncomendaMPResponse> findAll(Pageable pageable) {
        return encomendaMPRepository.findAllByIsActiveTrue(pageable).map(this::toResponse);
    }

    public Page<EncomendaMPResponse> findByEstado(EstadoEncomendaMP estado, Pageable pageable) {
        return encomendaMPRepository.findAllByEstadoAndIsActiveTrue(estado, pageable).map(this::toResponse);
    }

    public Page<EncomendaMPResponse> findByFornecedor(UUID fornecedorId, Pageable pageable) {
        return encomendaMPRepository.findAllByFornecedor_IdAndIsActiveTrue(fornecedorId, pageable).map(this::toResponse);
    }

    // Isto só o gestor do software pode fazer, o funcionario so cria  a encomenda
    @Transactional
    public EncomendaMPResponse aprovar(UUID id) {
        EncomendaMP encomenda = encomendaMPRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new EncomendaMPException(EncomendaMPErrorCode.ENCOMENDA_MP_NOT_FOUND));

        if (encomenda.getEstado() != EstadoEncomendaMP.PENDENTE)
            throw new EncomendaMPException(EncomendaMPErrorCode.TRANSICAO_ESTADO_INVALIDA);

        List<EncomendaMPLinha> linhas = encomendaMPLinhaRepository.findAllByEncomenda_IdAndIsActiveTrue(id);
        if (linhas.isEmpty())
            throw new EncomendaMPException(EncomendaMPErrorCode.SEM_LINHAS);

        encomenda.setEstado(EstadoEncomendaMP.ENCOMENDADA);
        return toResponse(encomendaMPRepository.save(encomenda));
    }

    @Transactional
    public EncomendaMPResponse marcarRecebida(UUID id) {
        EncomendaMP encomenda = encomendaMPRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new EncomendaMPException(EncomendaMPErrorCode.ENCOMENDA_MP_NOT_FOUND));

        if (encomenda.getEstado() != EstadoEncomendaMP.ENCOMENDADA)
            throw new EncomendaMPException(EncomendaMPErrorCode.TRANSICAO_ESTADO_INVALIDA);

        List<EncomendaMPLinha> linhas = encomendaMPLinhaRepository.findAllByEncomenda_IdAndIsActiveTrue(id);

        MovimentoStockMP movimento = new MovimentoStockMP(
                encomenda.getUser(),
                TipoMovimentoMP.ENTRADA,
                "Entrada automática — Encomenda #" + id
        );
        MovimentoStockMP savedMovimento = movimentoStockMPRepository.save(movimento);

        for (EncomendaMPLinha linha : linhas) {
            MovimentoStockMPMateria msmm = new MovimentoStockMPMateria(
                    savedMovimento, linha.getMateria(), linha.getQuantidade()
            );
            movimentoStockMPMateriaRepository.save(msmm);

            MateriaPrima materia = linha.getMateria();
            materia.setStockAtual(materia.getStockAtual().add(linha.getQuantidade()));
            materiaPrimaRepository.save(materia);
        }

        encomenda.setEstado(EstadoEncomendaMP.RECEBIDA);
        return toResponse(encomendaMPRepository.save(encomenda));
    }

    @Transactional
    public EncomendaMPResponse cancelar(UUID id) {
        EncomendaMP encomenda = encomendaMPRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new EncomendaMPException(EncomendaMPErrorCode.ENCOMENDA_MP_NOT_FOUND));

        if (encomenda.getEstado() != EstadoEncomendaMP.PENDENTE
                && encomenda.getEstado() != EstadoEncomendaMP.ENCOMENDADA)
            throw new EncomendaMPException(EncomendaMPErrorCode.ENCOMENDA_MP_CANCEL_FAILED);

        encomenda.setEstado(EstadoEncomendaMP.CANCELADA);
        return toResponse(encomendaMPRepository.save(encomenda));
    }

    private BigDecimal calcularTotalSemIva(List<EncomendaMPLinha> linhas, boolean useEur) {
        return linhas.stream()
                .map(l -> useEur
                        ? l.getPrecoUnitarioEur().multiply(l.getQuantidade())
                        : l.getPrecoUnitario().multiply(l.getQuantidade()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private EncomendaMPResponse toResponse(EncomendaMP e) {
        List<EncomendaMPLinha> linhas = encomendaMPLinhaRepository.findAllByEncomenda_IdAndIsActiveTrue(e.getId());
        return new EncomendaMPResponse(
                e.getId(),
                e.getUser().getId(),
                e.getUser().getNome(),
                e.getFornecedor().getId(),
                e.getFornecedor().getNome(),
                e.getMoeda().getId(),
                e.getMoeda().getCodigo(),
                e.getMoeda().getSimbolo(),
                e.getTaxaConversaoSnapshot(),
                e.getEstado(),
                e.getDataEncomenda(),
                e.getDataEntregaPrevista(),
                e.getTotalPrecoSemIva(),
                e.getTotalPrecoEurSemIva(),
                e.getTotalPrecoEurComIva(),
                e.getObservacoes(),
                linhas.stream().map(this::toLinhaResponse).toList(),
                e.isActive(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }

    private EncomendaMPLinhaResponse toLinhaResponse(EncomendaMPLinha l) {
        BigDecimal subtotal = l.getPrecoUnitario().multiply(l.getQuantidade()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal subtotalEur = l.getPrecoUnitarioEur().multiply(l.getQuantidade()).setScale(2, RoundingMode.HALF_UP);
        return new EncomendaMPLinhaResponse(
                l.getId(),
                l.getMateria().getId(),
                l.getMateria().getNome(),
                l.getMateria().getUnidade(),
                l.getQuantidade(),
                l.getPrecoUnitario(),
                l.getPrecoUnitarioEur(),
                l.getTaxaIva(),
                subtotal,
                subtotalEur,
                l.getCreatedAt()
        );
    }
}