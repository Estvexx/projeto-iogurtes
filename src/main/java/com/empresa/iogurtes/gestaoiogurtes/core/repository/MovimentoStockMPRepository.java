package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.MovimentoStockMP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovimentoStockMPRepository extends JpaRepository<MovimentoStockMP, UUID> {

    List<MovimentoStockMP> findAllByIsActiveTrue();
    List<MovimentoStockMP> findByMateriaId(UUID materiaId);
    List<MovimentoStockMP> findByUserId(UUID userId);

}