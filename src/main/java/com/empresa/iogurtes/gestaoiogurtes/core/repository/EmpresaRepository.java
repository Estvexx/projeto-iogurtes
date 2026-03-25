package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import org.springframework.stereotype.Repository;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {

    List<Empresa> findAllByIsActiveTrue();

    Optional<Empresa> findByNipc(String nipc);
    boolean existsByNipc(String nipc);
    boolean existsByNipcAndIdNot(String nipc, UUID id);

}