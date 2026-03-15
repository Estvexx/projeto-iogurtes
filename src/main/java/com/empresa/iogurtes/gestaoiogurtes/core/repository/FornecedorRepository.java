package com.empresa.iogurtes.gestaoiogurtes.core.repository;
import org.springframework.stereotype.Repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, UUID> {

    boolean existsByEmail(String email);
    boolean existsByNif(String nif);

}