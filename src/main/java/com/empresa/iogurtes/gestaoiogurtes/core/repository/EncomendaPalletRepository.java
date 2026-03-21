package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.EncomendaPallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EncomendaPalletRepository extends JpaRepository<EncomendaPallet, UUID> {
    List<EncomendaPallet> findByEncomendaId(UUID encomendaId);
}
