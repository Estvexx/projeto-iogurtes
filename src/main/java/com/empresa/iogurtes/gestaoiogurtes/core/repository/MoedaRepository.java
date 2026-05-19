package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Moeda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MoedaRepository extends JpaRepository<Moeda, UUID> {

    Optional<Moeda> findByIdAndIsActiveTrue(UUID id);

    Optional<Moeda> findByCodigoIgnoreCase(String codigo);

    boolean existsByCodigoIgnoreCase(String codigo);

    boolean existsByCodigoIgnoreCaseAndIdNot(String codigo, UUID id);

    Page<Moeda> findAllByIsActiveTrue(Pageable pageable);

    Page<Moeda> findAllByIsActiveFalse(Pageable pageable);
}