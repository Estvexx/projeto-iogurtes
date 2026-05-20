package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorCertificacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FornecedorCertificacaoRepository extends JpaRepository<FornecedorCertificacao, UUID> {

    Page<FornecedorCertificacao> findAllByIsActiveTrue(Pageable pageable);
    List<FornecedorCertificacao> findAllByFornecedor_IdAndIsActiveTrue(UUID id);
    Optional<FornecedorCertificacao>findByIdAndIsActiveIsTrue(UUID id);
    boolean existsByFornecedor_IdAndCertificacao_Id(UUID fornecedorId, UUID certificacaoId);
    List<FornecedorCertificacao> findAllByFornecedor_Id(UUID fornecedorId);
    // para o scheduler — busca todas as certificacoes com data_fim expirada e ainda ativas
    List<FornecedorCertificacao> findAllByDataFimBeforeAndIsActiveTrue(LocalDate data);
    boolean existsByCertificacao_IdAndIsActiveTrueAndDataFimAfter(UUID id, LocalDate data);
}