package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.ProdutoFinal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoFinalRepository extends JpaRepository<ProdutoFinal, UUID> {

    Optional<ProdutoFinal> findByIdAndIsActiveIsTrue(UUID id);

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, UUID id);

    Page<ProdutoFinal> findAllByIsActiveTrue(Pageable pageable);

    Page<ProdutoFinal> findAllByIsActiveFalse(Pageable pageable);

    Page<ProdutoFinal> findAllByIsActiveIsTrueAndVisivelClienteIsTrue(Pageable pageable);

    // Gerar próximo SKU — busca o último código gerado
    @Query("SELECT p.codigoSku FROM ProdutoFinal p ORDER BY p.createdAt DESC LIMIT 1")
    Optional<String> findLastCodigoSku();

}