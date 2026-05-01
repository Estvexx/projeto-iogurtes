package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorCertificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface FornecedorCertificacaoRepository extends JpaRepository<FornecedorCertificacao, UUID> {

    boolean existsByFornecedor_IdAndCertificacao_Id(UUID fornecedorId, UUID certificacaoId);
    List<FornecedorCertificacao> findAllByFornecedor_Id(UUID fornecedorId);
    List<FornecedorCertificacao> findAllByFornecedor_IdAndIsActiveTrue(UUID fornecedorId);
    // para o scheduler — busca todas as certificacoes com data_fim expirada e ainda ativas
    List<FornecedorCertificacao> findAllByDataFimBeforeAndIsActiveTrue(LocalDate data);
}