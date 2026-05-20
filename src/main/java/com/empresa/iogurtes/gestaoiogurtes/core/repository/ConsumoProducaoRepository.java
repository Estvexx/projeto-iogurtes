package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.ConsumoProducao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsumoProducaoRepository extends JpaRepository<ConsumoProducao, UUID> {

    List<ConsumoProducao> findAllByOrdem_IdAndIsActiveTrue(UUID ordemId);
}