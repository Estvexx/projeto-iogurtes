package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.MovimentoStockPF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MovimentoStockPFRepository extends JpaRepository<MovimentoStockPF, UUID> {

    List<MovimentoStockPF> findAllByIsActiveTrue();
    List<MovimentoStockPF> findByProdutoId(UUID produtoId);

    List<MovimentoStockPF> findByOrdemId(UUID ordemId);
}
