package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoLote;
import com.empresa.iogurtes.gestaoiogurtes.core.model.LoteProducao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoteProducaoRepository extends JpaRepository<LoteProducao, UUID> {

    Optional<LoteProducao> findByIdAndIsActiveIsTrue(UUID id);
    Page<LoteProducao> findAllByIsActiveTrue(Pageable pageable);

    // Lotes DISPONIVEL de um produto ordenados FEFO (data_validade ASC)
    List<LoteProducao> findAllByProduto_IdAndEstadoOrderByDataValidadeAsc(UUID produtoId, EstadoLote estado);

    Page<LoteProducao> findAllByProduto_IdAndIsActiveTrue(UUID produtoId, Pageable pageable);

    List<LoteProducao> findAllByOrdem_Id(UUID ordemId);

    @Query("SELECT COUNT(l) FROM LoteProducao l WHERE l.dataProducao = :data")
    long countByDataProducao(LocalDate data);

    List<LoteProducao> findAllByDataValidadeBeforeAndEstado(LocalDate data, EstadoLote estado);

    Optional<LoteProducao> findByNumeroLote(String numeroLote);
}