package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Certificacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CertificacaoRepository extends JpaRepository<Certificacao, UUID> {

    boolean existsByNomeAndIsActiveIsTrue(String nome);
    boolean existsByNomeAndIdNot(String nome, UUID id);
    Optional<Certificacao> findByIdAndIsActiveIsTrue(UUID id);
    Optional<Certificacao> findByNome(String nome);
    Page<Certificacao> findAllByIsActiveTrue(Pageable pageable);
    Page<Certificacao> findAllByIsActiveFalse(Pageable pageable);
}