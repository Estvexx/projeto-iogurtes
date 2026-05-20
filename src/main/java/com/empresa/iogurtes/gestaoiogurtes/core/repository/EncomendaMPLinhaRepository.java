package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.EncomendaMPLinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EncomendaMPLinhaRepository extends JpaRepository<EncomendaMPLinha, UUID> {

    List<EncomendaMPLinha> findAllByEncomenda_IdAndIsActiveTrue(UUID encomendaId);

    Optional<EncomendaMPLinha> findByIdAndIsActiveTrue(UUID id);

    boolean existsByEncomenda_IdAndMateria_Id(UUID encomendaId, UUID materiaId);
}