package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.OrdemProducaoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrdemProducaoProdutoRepository extends JpaRepository<OrdemProducaoProduto, UUID> {

    List<OrdemProducaoProduto> findAllByOrdem_IdAndIsActiveTrue(UUID ordemId);

    boolean existsByOrdem_IdAndProduto_Id(UUID ordemId, UUID produtoId);
}