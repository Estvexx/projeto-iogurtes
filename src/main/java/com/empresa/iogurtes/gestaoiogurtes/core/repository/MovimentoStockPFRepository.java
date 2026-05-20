package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.MovimentoStockPF;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MovimentoStockPFRepository extends JpaRepository<MovimentoStockPF, UUID> {

    Page<MovimentoStockPF> findAllByIsActiveTrue(Pageable pageable);

    List<MovimentoStockPF> findAllByLote_Id(UUID loteId);
}
