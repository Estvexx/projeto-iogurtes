package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoFisico;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.produto_final.*;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima.MateriaPrimaErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.materiaprima.MateriaPrimaException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.produtofinal.ProdutoFinalErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.produtofinal.ProdutoFinalException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.MateriaPrima;
import com.empresa.iogurtes.gestaoiogurtes.core.model.ProdutoFinal;
import com.empresa.iogurtes.gestaoiogurtes.core.model.ProdutoMateria;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MateriaPrimaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.ProdutoFinalRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.ProdutoMateriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdutoFinalService {

    private static final String SKU_PREFIX = "IOG-";
    private static final int SKU_PADDING = 5;

    private final ProdutoFinalRepository produtoFinalRepository;
    private final ProdutoMateriaRepository produtoMateriaRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;

    public ProdutoFinalService(ProdutoFinalRepository produtoFinalRepository,
                               ProdutoMateriaRepository produtoMateriaRepository,
                               MateriaPrimaRepository materiaPrimaRepository) {
        this.produtoFinalRepository = produtoFinalRepository;
        this.produtoMateriaRepository = produtoMateriaRepository;
        this.materiaPrimaRepository = materiaPrimaRepository;
    }

    @Transactional
    public ProdutoFinalResponse createProdutoFinal(CreateProdutoFinalRequest info) {
        if (produtoFinalRepository.existsByNomeIgnoreCase(info.nome()))
            throw new ProdutoFinalException(ProdutoFinalErrorCode.NOME_ALREADY_EXISTS);

        // Valida duplicados na composição enviada
        Set<UUID> materiaIds = info.composicao().stream()
                .map(CreateProdutoMateriaRequest::materiaId)// Guarda todas as materias _id
                .collect(Collectors.toSet()); // guardo de seguida num set (coleçao sem repetidos)

        if (materiaIds.size() != info.composicao().size())
            throw new ProdutoFinalException(ProdutoFinalErrorCode.MATERIA_DUPLICADA_COMPOSICAO);

        // Gera SKU automaticamente
        String codigoSku = gerarProximoSku(info.abreviacaoSabor(), info.estadoFisico());

        try {
            ProdutoFinal produto = new ProdutoFinal(
                    codigoSku,
                    info.nome(),
                    info.descricao(),
                    info.abreviacaoSabor(),
                    info.estadoFisico(),
                    info.validadeDias(),
                    info.precoVenda(),
                    info.precoPorKg(),
                    info.taxaIva(),
                    info.visivelCliente(),
                    info.quantidadeLote()
            );
            ProdutoFinal saved = produtoFinalRepository.save(produto);

            // Guardar composição
            for (CreateProdutoMateriaRequest comp : info.composicao()) {
                MateriaPrima materia = materiaPrimaRepository.findByIdAndIsActiveIsTrue(comp.materiaId())
                        .orElseThrow(() -> new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));

                produtoMateriaRepository.save(new ProdutoMateria(saved, materia, comp.quantidadePorUnidadeProduto()));
            }

            return toResponse(saved);
        } catch (ProdutoFinalException | MateriaPrimaException e) {
            throw e;
        } catch (Exception e) {
            throw new ProdutoFinalException(ProdutoFinalErrorCode.PRODUTO_FINAL_CREATE_FAILED);
        }
    }

    public ProdutoFinalResponse findById(UUID id) {
        return produtoFinalRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ProdutoFinalException(ProdutoFinalErrorCode.PRODUTO_FINAL_NOT_FOUND));
    }

    public Page<ProdutoFinalResponse> findAllActive(Pageable pageable) {
        return produtoFinalRepository.findAllByIsActiveTrue(pageable).map(this::toResponse);
    }

    public Page<ProdutoFinalResponse> findAllInactive(Pageable pageable) {
        return produtoFinalRepository.findAllByIsActiveFalse(pageable).map(this::toResponse);
    }

    public Page<ProdutoFinalResponse> findAllVisivelCliente(Pageable pageable) {
        return produtoFinalRepository.findAllByIsActiveIsTrueAndVisivelClienteIsTrue(pageable).map(this::toResponse);
    }

    @Transactional
    public ProdutoFinalResponse updateProdutoFinal(UUID id, UpdateProdutoFinalRequest info) {
        ProdutoFinal produto = produtoFinalRepository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new ProdutoFinalException(ProdutoFinalErrorCode.PRODUTO_FINAL_NOT_FOUND));

        if (produtoFinalRepository.existsByNomeIgnoreCaseAndIdNot(info.nome(), id))
            throw new ProdutoFinalException(ProdutoFinalErrorCode.NOME_ALREADY_EXISTS);

        try {
            produto.setNome(info.nome());
            produto.setDescricao(info.descricao());
            produto.setAbreviacaoSabor(info.abreviacaoSabor());
            produto.setEstadoFisico(info.estadoFisico());
            produto.setValidadeDias(info.validadeDias());
            produto.setPrecoVenda(info.precoVenda());
            produto.setPrecoPorKg(info.precoPorKg());
            produto.setTaxaIva(info.taxaIva());
            produto.setVisivelCliente(info.visivelCliente());
            produto.setQuantidadeLote(info.quantidadeLote());
            return toResponse(produtoFinalRepository.save(produto));
        } catch (Exception e) {
            throw new ProdutoFinalException(ProdutoFinalErrorCode.PRODUTO_FINAL_UPDATE_FAILED);
        }
    }

    @Transactional
    public void softDelete(UUID id) {
        ProdutoFinal produto = produtoFinalRepository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new ProdutoFinalException(ProdutoFinalErrorCode.PRODUTO_FINAL_NOT_FOUND));

        // Cascade softDelete na composição
        List<ProdutoMateria> composicao = produtoMateriaRepository.findAllByProduto_IdAndIsActiveTrue(id);
        composicao.forEach(pm -> {
            pm.softDelete();
            produtoMateriaRepository.save(pm);
        });

        produto.softDelete();
        produtoFinalRepository.save(produto);
    }

    // ─── COMPOSIÇÃO ───────────────────────────────────────────────────────────

    @Transactional
    public ProdutoFinalResponse addMaterias(UUID produtoId, AddMateriasComposicaoRequest info) {
        ProdutoFinal produto = produtoFinalRepository.findByIdAndIsActiveIsTrue(produtoId)
                .orElseThrow(() -> new ProdutoFinalException(ProdutoFinalErrorCode.PRODUTO_FINAL_NOT_FOUND));

        // Valida duplicados no request
        Set<UUID> ids = info.materias().stream()
                .map(CreateProdutoMateriaRequest::materiaId)
                .collect(Collectors.toSet());
        if (ids.size() != info.materias().size())
            throw new ProdutoFinalException(ProdutoFinalErrorCode.MATERIA_DUPLICADA_COMPOSICAO);

        for (CreateProdutoMateriaRequest comp : info.materias()) {
            MateriaPrima materia = materiaPrimaRepository.findByIdAndIsActiveIsTrue(comp.materiaId())
                    .orElseThrow(() -> new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));

            if (produtoMateriaRepository.existsByProduto_IdAndMateria_Id(produtoId, comp.materiaId()))
                throw new ProdutoFinalException(ProdutoFinalErrorCode.MATERIA_JA_NA_COMPOSICAO);

            produtoMateriaRepository.save(new ProdutoMateria(produto, materia, comp.quantidadePorUnidadeProduto()));
        }

        return toResponse(produto);
    }

    @Transactional
    public ProdutoFinalResponse updateComposicao(UUID produtoId, UpdateComposicaoRequest info) {
        ProdutoFinal produto = produtoFinalRepository.findByIdAndIsActiveIsTrue(produtoId)
                .orElseThrow(() -> new ProdutoFinalException(ProdutoFinalErrorCode.PRODUTO_FINAL_NOT_FOUND));

        // Valida duplicados no request
        Set<UUID> ids = info.materias().stream()
                .map(CreateProdutoMateriaRequest::materiaId)
                .collect(Collectors.toSet());
        if (ids.size() != info.materias().size())
            throw new ProdutoFinalException(ProdutoFinalErrorCode.MATERIA_DUPLICADA_COMPOSICAO);

        // SoftDelete a composição atual
        List<ProdutoMateria> composicaoAtual = produtoMateriaRepository.findAllByProduto_IdAndIsActiveTrue(produtoId);
        composicaoAtual.forEach(pm -> {
            pm.softDelete();
            produtoMateriaRepository.save(pm);
        });

        // Inserir nova composição
        for (CreateProdutoMateriaRequest comp : info.materias()) {
            MateriaPrima materia = materiaPrimaRepository.findByIdAndIsActiveIsTrue(comp.materiaId())
                    .orElseThrow(() -> new MateriaPrimaException(MateriaPrimaErrorCode.MATERIA_PRIMA_NOT_FOUND));

            produtoMateriaRepository.save(new ProdutoMateria(produto, materia, comp.quantidadePorUnidadeProduto()));
        }

        return toResponse(produto);
    }

    @Transactional
    public ProdutoFinalResponse removeMateria(UUID produtoId, UUID composicaoId) {
        ProdutoFinal produto = produtoFinalRepository.findByIdAndIsActiveIsTrue(produtoId)
                .orElseThrow(() -> new ProdutoFinalException(ProdutoFinalErrorCode.PRODUTO_FINAL_NOT_FOUND));

        ProdutoMateria pm = produtoMateriaRepository.findByIdAndIsActiveIsTrue(composicaoId)
                .orElseThrow(() -> new ProdutoFinalException(ProdutoFinalErrorCode.PRODUTO_MATERIA_NOT_FOUND));

        // Garante que o produto não fica sem composição
        List<ProdutoMateria> composicao = produtoMateriaRepository.findAllByProduto_IdAndIsActiveTrue(produtoId);
        if (composicao.size() <= 1)
            throw new ProdutoFinalException(ProdutoFinalErrorCode.COMPOSICAO_OBRIGATORIA);

        pm.softDelete();
        produtoMateriaRepository.save(pm);
        return toResponse(produto);
    }

    private String gerarProximoSku(String abreviacaoSabor, EstadoFisico estadoFisico) {
        String sufixoEstado = estadoFisico == EstadoFisico.LIQUIDO ? "LIQ" : "SOL";

        String sequencia = produtoFinalRepository.findLastCodigoSku()
                .map(lastSku -> {
                    // Extrai o número do último SKU (ex: "IOG-001-MOR-LIQ" → 1)
                    String[] partes = lastSku.split("-");
                    int nextNum = Integer.parseInt(partes[1]) + 1;
                    return String.format("%0" + SKU_PADDING + "d", nextNum);
                })
                .orElse(String.format("%0" + SKU_PADDING + "d", 1));

        return SKU_PREFIX + sequencia + "-" + abreviacaoSabor.toUpperCase() + "-" + sufixoEstado;
    }

    private ProdutoFinalResponse toResponse(ProdutoFinal p) {
        List<ProdutoMateria> composicao = produtoMateriaRepository.findAllByProduto_IdAndIsActiveTrue(p.getId());
        return new ProdutoFinalResponse(
                p.getId(),
                p.getCodigoSku(),
                p.getNome(),
                p.getDescricao(),
                p.getAbreviacaoSabor(),
                p.getEstadoFisico(),
                p.getValidadeDias(),
                p.getPrecoVenda(),
                p.getPrecoPorKg(),
                p.getTaxaIva(),
                p.isVisivelCliente(),
                p.getQuantidadeLote(),
                composicao.stream().map(this::toMateriaResponse).toList(),
                p.isActive(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }

    private ProdutoMateriaResponse toMateriaResponse(ProdutoMateria pm) {
        return new ProdutoMateriaResponse(
                pm.getId(),
                pm.getMateria().getId(),
                pm.getMateria().getNome(),
                pm.getMateria().getUnidade(),
                pm.getQuantidadePorUnidadeProduto()
        );
    }
}