package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomenda;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Encomenda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EncomendaRepository extends JpaRepository<Encomenda, UUID> {

    Optional<Encomenda> findByIdAndIsActiveTrue(UUID id);

    Page<Encomenda> findAllByIsActiveTrue(Pageable pageable);

    Page<Encomenda> findAllByEstadoAndIsActiveTrue(EstadoEncomenda estado, Pageable pageable);

    Page<Encomenda> findAllByUser_IdAndIsActiveTrue(UUID userId, Pageable pageable);
}