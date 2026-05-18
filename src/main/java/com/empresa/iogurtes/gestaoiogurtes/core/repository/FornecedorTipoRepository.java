package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FornecedorTipoRepository extends JpaRepository<FornecedorTipo, UUID> {

    boolean existsByNome(String nome);
    boolean existsByNomeAndIdNot(String nome, UUID id);
    Optional<FornecedorTipo> findByNome(String nome);
    List<FornecedorTipo> findAllByIsActiveTrue();
    List<FornecedorTipo> findAllByIsActiveFalse();
}
