package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Certificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CertificacaoRepository extends JpaRepository<Certificacao, UUID> {

    boolean existsByNome(String nome);
    boolean existsByNomeAndIdNot(String nome, UUID id);
    Optional<Certificacao> findByNome(String nome);
    List<Certificacao> findAllByIsActiveTrue();
    List<Certificacao> findAllByIsActiveFalse();
}