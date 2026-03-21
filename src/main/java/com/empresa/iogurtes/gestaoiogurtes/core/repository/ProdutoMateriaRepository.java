package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.ProdutoMateria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface ProdutoMateriaRepository extends JpaRepository<ProdutoMateria, UUID> {
    //List<ProdutoMateria> findByProdutoId(UUID produtoId);
}
