package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Fornecedor;
import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorCertificacao;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MateriaPrimaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.FornecedorValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;
    private final MateriaPrimaService materiaPrimaService;
    private final FornecedorValidator fornecedorValidator;

    public FornecedorService(FornecedorRepository fornecedorRepository,
                             MateriaPrimaRepository materiaPrimaRepository,
                             MateriaPrimaService materiaPrimaService,
                             FornecedorValidator fornecedorValidator) {
        this.fornecedorRepository = fornecedorRepository;
        this.materiaPrimaRepository = materiaPrimaRepository;
        this.materiaPrimaService = materiaPrimaService;
        this.fornecedorValidator = fornecedorValidator;
    }

    @Transactional
    public Fornecedor createFornecedor(String nome, String nif, String email,
                                       String telefone, String morada, List<FornecedorCertificacao> certificacoes) {

        fornecedorValidator.validateCreateFornecedor(nome, nif, email, morada,  certificacoes);
        String telefoneNormalizado = fornecedorValidator.validarTelefone(telefone);

        Fornecedor fornecedor = new Fornecedor(nome, nif, email, telefoneNormalizado, morada);

        for (FornecedorCertificacao certificacao : certificacoes) {
            certificacao.setFornecedor(fornecedor);
        }

        fornecedor.setCertificacoes(certificacoes);
        return fornecedorRepository.save(fornecedor);
    }

    @Transactional
    public Fornecedor updateFornecedor(UUID id, String nome, String nif, String email,
                                       String telefone, String morada) {

        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));

        fornecedorValidator.validateUpdateFornecedor(nome, nif, email, morada);
        String telefoneNormalizado = fornecedorValidator.validarTelefone(telefone);

        fornecedor.setNome(nome);
        fornecedor.setNif(nif);
        fornecedor.setEmail(email);
        fornecedor.setTelefone(telefoneNormalizado);
        fornecedor.setMorada(morada);

        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor getById(UUID id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));
    }

    public List<Fornecedor> getAll() {
        return fornecedorRepository.findAllByIsActiveTrue();
    }

    public List<Fornecedor> getAllIncludingInactive() {
        return fornecedorRepository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));

        materiaPrimaRepository.findByFornecedorId(id)
                .forEach(mp -> materiaPrimaService.delete(mp.getId()));

        fornecedor.getCertificacoes().forEach(FornecedorCertificacao::softDelete);

        fornecedor.softDelete();
        fornecedorRepository.save(fornecedor);
    }
}