package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.MovimentoStockMPMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovimentoStockMPMateriaRepository extends JpaRepository<MovimentoStockMPMateria, UUID> {

    List<MovimentoStockMPMateria> findAllByMovimento_Id(UUID movimentoId);
}
