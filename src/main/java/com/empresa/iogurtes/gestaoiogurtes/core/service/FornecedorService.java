package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Fornecedor;
import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorCertificacao;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.FornecedorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final FornecedorValidator fornecedorValidator;

    public FornecedorService(FornecedorRepository fornecedorRepository, FornecedorValidator fornecedorValidator) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorValidator = fornecedorValidator;
    }

    public Fornecedor createFornecedor(String nome, String nif, String email,
                                       String telefone, String morada, List<FornecedorCertificacao> certificacoes) {

        fornecedorValidator.validateCreateFornecedor(nome, nif, email, telefone, morada,  certificacoes);

        Fornecedor fornecedor = new Fornecedor(nome, nif, email, telefone, morada);

        for (FornecedorCertificacao certificacao : certificacoes) {
            certificacao.setFornecedor(fornecedor);
        }

        fornecedor.setCertificacoes(certificacoes);
        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor updateFornecedor(UUID id, String nome, String nif, String email,
                                       String telefone, String morada) {

        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));

        fornecedorValidator.validateUpdateFornecedor(nome, nif, email, telefone, morada);

        fornecedor.setNome(nome);
        fornecedor.setNif(nif);
        fornecedor.setEmail(email);
        fornecedor.setTelefone(telefone);
        fornecedor.setMorada(morada);

        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor getById(UUID id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));
    }

    public List<Fornecedor> getAll() {
        return fornecedorRepository.findAll();
    }

    public void delete(UUID id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));

        fornecedorRepository.delete(fornecedor);
    }
}