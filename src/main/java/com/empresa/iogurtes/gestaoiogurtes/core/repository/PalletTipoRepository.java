package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.PalletTipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface PalletTipoRepository extends JpaRepository<PalletTipo, UUID> {

    boolean existsByNomeIgnoreCase(String nome);
    boolean existsByCapacidadeKg(BigDecimal capacidadeKg);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, UUID id);

    Optional<PalletTipo> findByIdAndIsActiveTrue(UUID id);

    Page<PalletTipo> findAllByIsActiveTrue(Pageable pageable);

    Page<PalletTipo> findAllByIsActiveFalse(Pageable pageable);
}