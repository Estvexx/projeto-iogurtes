package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.ProdutoMateria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoMateriaRepository extends JpaRepository<ProdutoMateria, UUID> {
    List<ProdutoMateria> findAllByProduto_IdAndIsActiveTrue(UUID produtoId);

    Optional<ProdutoMateria> findByIdAndIsActiveIsTrue(UUID id);

    boolean existsByProduto_IdAndMateria_Id(UUID produtoId, UUID materiaId);

    boolean existsByProduto_IdAndMateria_IdAndIdNot(UUID produtoId, UUID materiaId, UUID id);

    boolean existsByMateria_IdAndIsActiveTrue(UUID materiaId);

    // usado no cascade softDelete quando MateriaPrima é eliminada
    List<ProdutoMateria> findAllByMateria_IdAndIsActiveTrue(UUID materiaId);
}
