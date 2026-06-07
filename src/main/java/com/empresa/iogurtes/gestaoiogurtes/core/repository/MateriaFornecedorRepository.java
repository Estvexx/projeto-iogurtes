package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.MateriaFornecedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MateriaFornecedorRepository extends JpaRepository<MateriaFornecedor, UUID> {

    Optional<MateriaFornecedor> findByIdAndIsActiveTrue(UUID id);

    boolean existsByMateria_IdAndFornecedor_Id(UUID materiaId, UUID fornecedorId);

    boolean existsByMateria_IdAndFornecedor_IdAndIdNot(UUID materiaId, UUID fornecedorId, UUID id);

    Optional<MateriaFornecedor>findByMateria_IdAndFornecedor_IdAndIsActiveIsTrue(UUID materiaId, UUID fornecedorId);
    boolean existsByMateria_IdAndFornecedor_IdAndIsActiveIsTrue(
            UUID materiaId,
            UUID fornecedorId
    );
    Page<MateriaFornecedor> findAllByMateria_IdAndIsActiveTrue(UUID materiaId, Pageable pageable);
    Page<MateriaFornecedor> findAllByIsActiveIsTrue(Pageable pageable);

    // usado no cascade softDelete quando MateriaPrima é eliminada
    List<MateriaFornecedor> findAllByMateria_IdAndIsActiveTrue(UUID materiaId);

    // usado no cascade softDelete quando Fornecedor é eliminado (para uso futuro no FornecedorService)
    List<MateriaFornecedor> findAllByFornecedor_IdAndIsActiveTrue(UUID fornecedorId);
}