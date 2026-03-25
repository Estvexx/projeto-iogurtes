package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.ProdutoFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProdutoFinalRepository extends JpaRepository<ProdutoFinal, UUID> {

    List<ProdutoFinal> findAllByIsActiveTrue();
    boolean existsByCodigoSku(String codigoSku);
    boolean existsByNome(String nome);
    boolean existsByNomeAndIdNot(String nome, UUID id);
    boolean existsByCodigoSkuAndIdNot(String codigoSku, UUID id);

}