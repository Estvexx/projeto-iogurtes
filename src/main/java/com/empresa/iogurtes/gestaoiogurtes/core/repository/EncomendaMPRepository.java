package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.EncomendaMP;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomendaMP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EncomendaMPRepository extends JpaRepository<EncomendaMP, UUID> {

    Optional<EncomendaMP> findByIdAndIsActiveTrue(UUID id);

    Page<EncomendaMP> findAllByIsActiveTrue(Pageable pageable);

    Page<EncomendaMP> findAllByEstadoAndIsActiveTrue(EstadoEncomendaMP estado, Pageable pageable);

    Page<EncomendaMP> findAllByFornecedor_IdAndIsActiveTrue(UUID fornecedorId, Pageable pageable);
}