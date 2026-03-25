package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.OrdemProducao;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoOrdem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, UUID> {
    List<OrdemProducao> findAllByIsActiveTrue();
    List<OrdemProducao> findByUserId(UUID userId);
    // notIn é para evitar processar ordens que já estão concluídas ou canceladas
    List<OrdemProducao> findByDataFimBeforeAndEstadoNotIn(LocalDateTime data, List<EstadoOrdem> estados);
}
