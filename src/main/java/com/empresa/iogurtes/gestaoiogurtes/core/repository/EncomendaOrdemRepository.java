package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomendaOrdem;
import com.empresa.iogurtes.gestaoiogurtes.core.model.EncomendaOrdem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EncomendaOrdemRepository extends JpaRepository<EncomendaOrdem, UUID> {

    List<EncomendaOrdem> findAllByEncomendaPallet_Encomenda_IdAndIsActiveTrue(UUID encomendaId);

    List<EncomendaOrdem> findAllByEstadoAndIsActiveTrue(EstadoEncomendaOrdem estado);

    Optional<EncomendaOrdem> findByOrdem_IdAndIsActiveTrue(UUID ordemId);
}