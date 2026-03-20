package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.OrdemProducao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, UUID> {
}
