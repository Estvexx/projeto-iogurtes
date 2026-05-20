package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.EncomendaPallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EncomendaPalletRepository extends JpaRepository<EncomendaPallet, UUID> {

    List<EncomendaPallet> findAllByEncomenda_IdAndIsActiveTrue(UUID encomendaId);
}