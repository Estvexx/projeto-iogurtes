package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {

    Optional<Empresa> findByIdAndIsActiveIsTrue(UUID id);
    boolean existsByNipc(String nipc);
    Page<Empresa> findAllByIsActiveTrue(Pageable pageable);
    Page<Empresa> findAllByIsActiveFalse(Pageable pageable);

    boolean existsByNipcAndIdNot(String nipc, UUID id);
}