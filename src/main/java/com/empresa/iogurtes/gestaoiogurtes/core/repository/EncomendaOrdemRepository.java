package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.EncomendaOrdem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EncomendaOrdemRepository extends JpaRepository<EncomendaOrdem, UUID> {
    List<EncomendaOrdem> findAllByIsActiveTrue();
    List<EncomendaOrdem> findByEncomendaPalletId(UUID encomendaPalletId);
    List<EncomendaOrdem> findByOrdemId(UUID ordemId);
}