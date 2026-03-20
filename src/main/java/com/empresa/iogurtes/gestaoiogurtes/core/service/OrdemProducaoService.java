package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.ConsumoProducao;
import com.empresa.iogurtes.gestaoiogurtes.core.model.OrdemProducao;
import com.empresa.iogurtes.gestaoiogurtes.core.model.ProdutoFinal;
import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.OrdemProducaoRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.ProdutoFinalRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrdemProducaoService {

    private final OrdemProducaoRepository ordemRepository;
    private final ProdutoFinalRepository produtoFinalRepository;
    private final UserRepository userRepository;

    public OrdemProducaoService(OrdemProducaoRepository ordemRepository,
                                ProdutoFinalRepository produtoFinalRepository,
                                UserRepository userRepository) {
        this.ordemRepository = ordemRepository;
        this.produtoFinalRepository = produtoFinalRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrdemProducao createOrdem(UUID produtoId, UUID userId,
                                     BigDecimal quantidadeKg, String observacoes) {

        ProdutoFinal produto = produtoFinalRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado"));

        OrdemProducao ordem = new OrdemProducao(produto, quantidadeKg, user, observacoes);

        List<ConsumoProducao> consumos = produto.getMaterias().stream()
                .map(pm -> {
                    BigDecimal consumoTotal = pm.getQuantidadePorUnidadeProduto()
                            .multiply(quantidadeKg);
                    return new ConsumoProducao(ordem, pm.getMateria(), consumoTotal);
                })
                .toList();

        ordem.setConsumos(consumos);

        return ordemRepository.save(ordem);
    }
}
