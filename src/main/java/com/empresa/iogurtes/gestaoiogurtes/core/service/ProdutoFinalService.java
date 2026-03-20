package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.MateriaPrima;
import com.empresa.iogurtes.gestaoiogurtes.core.model.ProdutoFinal;
import com.empresa.iogurtes.gestaoiogurtes.core.model.ProdutoMateria;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MateriaPrimaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.ProdutoFinalRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.ProdutoMateriaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.ProdutoMateriaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.ProdutoFinalValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProdutoFinalService {

    private final ProdutoFinalRepository produtoFinalRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;
    private final ProdutoFinalValidator produtoFinalValidator;
    private final ProdutoMateriaRepository produtoMateriaRepository;

    public ProdutoFinalService(ProdutoFinalRepository produtoFinalRepository,
                               MateriaPrimaRepository materiaPrimaRepository,
                               ProdutoFinalValidator produtoFinalValidator,
                               ProdutoMateriaRepository produtoMateriaRepository) {
        this.produtoFinalRepository = produtoFinalRepository;
        this.materiaPrimaRepository = materiaPrimaRepository;
        this.produtoFinalValidator = produtoFinalValidator;
        this.produtoMateriaRepository = produtoMateriaRepository;
    }

    @Transactional
    public ProdutoFinal createProduto(String codigoSku, String nome, String descricao,
                                      Integer validadeDias, BigDecimal precoVenda,
                                      BigDecimal precoPorKg, Integer quantidadeLote,
                                      List<ProdutoMateria> materias) {

        produtoFinalValidator.validateCreateProduto(codigoSku, nome, precoVenda, precoPorKg, quantidadeLote, materias);

        ProdutoFinal produto = new ProdutoFinal(codigoSku, nome, descricao, validadeDias,
                precoVenda, precoPorKg, quantidadeLote);

        for (ProdutoMateria materia : materias) {
            materia.setProduto(produto);
        }

        produto.setMaterias(materias);

        return produtoFinalRepository.save(produto);
    }

    @Transactional
    public ProdutoFinal updateProduto(UUID id, String nome, String descricao,
                                      Integer validadeDias, BigDecimal precoVenda,
                                      BigDecimal precoPorKg, Integer quantidadeLote,
                                      Boolean visivelCliente) {

        ProdutoFinal produto = produtoFinalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado!"));

        produtoFinalValidator.validateUpdateProduto(id, nome, precoVenda, precoPorKg, quantidadeLote);

        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setValidadeDias(validadeDias);
        produto.setPrecoVenda(precoVenda);
        produto.setPrecoPorKg(precoPorKg);
        produto.setQuantidadeLote(quantidadeLote);
        produto.setVisivelCliente(visivelCliente);

        return produtoFinalRepository.save(produto);
    }

    public ProdutoFinal getById(UUID id) {
        return produtoFinalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado!"));
    }

    public List<ProdutoFinal> getAll() {
        return produtoFinalRepository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        ProdutoFinal produto = produtoFinalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado!"));

        produtoFinalRepository.delete(produto);
    }
/*
    @Transactional(readOnly = true)
    public List<ProdutoMateria> getMateriasByProdutoId(UUID id) {

        return produtoMateriaRepository.findByProdutoId(id);
    }*/
}