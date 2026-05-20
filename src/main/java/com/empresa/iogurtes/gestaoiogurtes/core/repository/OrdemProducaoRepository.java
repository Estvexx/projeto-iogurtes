package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoOrdem;
import com.empresa.iogurtes.gestaoiogurtes.core.model.OrdemProducao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, UUID> {

    Optional<OrdemProducao> findByIdAndIsActiveTrue(UUID id);

    Page<OrdemProducao> findAllByIsActiveTrue(Pageable pageable);

    Page<OrdemProducao> findAllByEstadoAndIsActiveTrue(EstadoOrdem estado, Pageable pageable);

    Page<OrdemProducao> findAllByUser_IdAndIsActiveTrue(UUID userId, Pageable pageable);
}