package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.TipoMateria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TipoMateriaRepository extends JpaRepository<TipoMateria, UUID> {

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, UUID id);

    Optional<TipoMateria> findByIdAndIsActiveIsTrue(UUID id);

    Page<TipoMateria> findAllByIsActiveTrue(Pageable pageable);

    Page<TipoMateria> findAllByIsActiveFalse(Pageable pageable);
}