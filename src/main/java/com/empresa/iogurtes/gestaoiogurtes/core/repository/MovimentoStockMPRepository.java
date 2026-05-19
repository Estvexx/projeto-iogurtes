package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.MovimentoStockMP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovimentoStockMPRepository extends JpaRepository<MovimentoStockMP, UUID> {

    Page<MovimentoStockMP> findAllByIsActiveTrue(Pageable pageable);

    Page<MovimentoStockMP> findAllByUser_IdAndIsActiveTrue(UUID userId, Pageable pageable);
}