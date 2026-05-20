package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorTipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FornecedorTipoRepository extends JpaRepository<FornecedorTipo, UUID> {
    boolean existsByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, UUID id);
    Page<FornecedorTipo> findAllByIsActiveTrue(Pageable pageable);
    Page<FornecedorTipo> findAllByIsActiveFalse(Pageable pageable);
    Optional <FornecedorTipo> findByIdAndIsActiveIsTrue(UUID id);
}
