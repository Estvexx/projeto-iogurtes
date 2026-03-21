package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.PalletTipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PalletTipoRepository extends JpaRepository<PalletTipo, UUID> {

    boolean existsByNome(String nome);
    List<PalletTipo> findByIsActiveTrue();
}