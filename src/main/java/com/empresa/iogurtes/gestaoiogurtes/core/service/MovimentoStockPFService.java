package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.MovimentoStockPF;
import com.empresa.iogurtes.gestaoiogurtes.core.model.OrdemProducao;
import com.empresa.iogurtes.gestaoiogurtes.core.model.ProdutoFinal;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoPF;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MovimentoStockPFRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.ProdutoFinalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MovimentoStockPFService {

    private final MovimentoStockPFRepository movimentoRepository;
    private final ProdutoFinalRepository produtoFinalRepository;

    public MovimentoStockPFService(MovimentoStockPFRepository movimentoRepository,
                                   ProdutoFinalRepository produtoFinalRepository) {
        this.movimentoRepository = movimentoRepository;
        this.produtoFinalRepository = produtoFinalRepository;
    }


    @Transactional
    public MovimentoStockPF registarMovimento(ProdutoFinal produto, OrdemProducao ordem,
                                              TipoMovimentoPF tipo, Integer quantidade,
                                              String observacao) {
        switch (tipo) {
            case PRODUCAO, DEVOLUCAO -> produto.setStockAtual(produto.getStockAtual() + quantidade);
            case EXPEDICAO           -> {
                if (produto.getStockAtual() - quantidade < 0)
                    throw new IllegalStateException("Stock insuficiente para o produto: " + produto.getNome());
                produto.setStockAtual(produto.getStockAtual() - quantidade);
            }
            case AJUSTE              -> produto.setStockAtual(quantidade);
        }

        produtoFinalRepository.save(produto);

        return movimentoRepository.save(
                new MovimentoStockPF(produto, ordem, tipo, quantidade, observacao)
        );
    }

    public MovimentoStockPF getById(UUID id) {
        return movimentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimento não encontrado"));
    }

    public List<MovimentoStockPF> getAll() {
        return movimentoRepository.findAllByIsActiveTrue();
    }

    public List<MovimentoStockPF> getAllIncludingInactive() {
        return movimentoRepository.findAll();
    }

    public List<MovimentoStockPF> getByProduto(UUID produtoId) {
        return movimentoRepository.findByProdutoId(produtoId);
    }

    public List<MovimentoStockPF> getByOrdem(UUID ordemId) {
        return movimentoRepository.findByOrdemId(ordemId);
    }
}