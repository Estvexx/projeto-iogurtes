package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, UUID> {

    boolean existsByNif(String nif);
    boolean existsByNifAndIdNot(String nif, UUID id);
    List<Fornecedor> findAllByIsActiveTrue();
    List<Fornecedor> findAllByIsActiveFalse();
    List<Fornecedor> findAllByTipo_IdAndIsActiveTrue(UUID tipoId);
    boolean existsByTipo_IdAndIsActiveTrue(UUID tipoId);
}