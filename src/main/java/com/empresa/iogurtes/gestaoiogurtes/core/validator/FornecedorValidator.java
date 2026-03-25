package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.model.FornecedorCertificacao;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.utils.PhoneUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FornecedorValidator {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorValidator(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public void validateCreateFornecedor(String nome, String nif, String email,
                                         String morada, List<FornecedorCertificacao> certificacoes) {
        validarNome(nome);
        validarNif(nif);
        validarEmail(email);
        validarMorada(morada);
        validarCertificacoes(certificacoes);
    }

    public void validateUpdateFornecedor(String nome, String nif, String email,
                                          String morada) {
        validarNome(nome);
        validarNif(nif);
        validarEmail(email);
        validarMorada(morada);
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (nome.length() < 2 || nome.length() > 150) {
            throw new IllegalArgumentException("Nome deve ter entre 2 e 150 caracteres");
        }
    }

    private void validarNif(String nif) {
        if (nif != null && fornecedorRepository.existsByNif(nif)) {
            throw new IllegalArgumentException("Já existe um fornecedor com este NIF");
        }
    }

    private void validarEmail(String email) {
        if (email != null && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
    }

    public String validarTelefone(String telefone) {
        return PhoneUtils.validarENormalizar(telefone);
    }

    private void validarMorada(String morada) {
        if (morada != null && morada.length() > 200) {
            throw new IllegalArgumentException("Morada não pode ter mais de 200 caracteres");
        }
    }

    private void validarCertificacoes(List<FornecedorCertificacao> certificacoes) {
        if (certificacoes == null || certificacoes.isEmpty()) {
            return; // opcional, não é obrigatório
        }

        for (FornecedorCertificacao cert : certificacoes) {
            if (cert.getTipo() == null) {
                throw new IllegalArgumentException("Tipo de certificação é obrigatório");
            }
            if (cert.getValidade() != null && cert.getValidade().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("A validade da certificação não pode ser no passado");
            }
        }
    }
}