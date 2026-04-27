package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {

    boolean existsByNipc(String nipc);
    List<Empresa> findAllByIsActiveTrue();
    List<Empresa> findAllByIsActiveFalse();

    boolean existsByNipcAndIdNot(String nipc, UUID id);
}