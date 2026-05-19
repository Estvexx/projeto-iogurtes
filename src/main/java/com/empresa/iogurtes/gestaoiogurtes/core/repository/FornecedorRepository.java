package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Fornecedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, UUID> {

    boolean existsByNif(String nif);
    boolean existsByNifAndIdNot(String nif, UUID id);
    Optional<Fornecedor> findByIdAndIsActiveIsTrue(UUID id);
    Page<Fornecedor> findAllByIsActiveTrue(Pageable pageable);
    Page<Fornecedor> findAllByIsActiveFalse(Pageable pageable);
    Page<Fornecedor> findAllByTipo_IdAndIsActiveTrue(UUID tipoId, Pageable pageable);
    boolean existsByTipo_IdAndIsActiveTrue(UUID tipoId);
}