package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMateriaPrima;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MateriaPrimaRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class MateriaPrimaValidator {

    private final MateriaPrimaRepository materiaPrimaRepository;
    private final FornecedorRepository fornecedorRepository;

    public MateriaPrimaValidator(MateriaPrimaRepository materiaPrimaRepository,
                                 FornecedorRepository fornecedorRepository) {
        this.materiaPrimaRepository = materiaPrimaRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    public void validateCreateMateriaPrima(String nome,TipoMateriaPrima tipo, String unidade,
                                           BigDecimal stockAtual, BigDecimal stockMinimo,
                                           BigDecimal precoUnitario, UUID fornecedorId) {
        validarNome(nome);
        validarTipo(tipo);
        validarUnidade(unidade);
        validarStockAtual(stockAtual);
        validarStockMinimo(stockMinimo);
        validarPrecoUnitario(precoUnitario);
        validarFornecedor(fornecedorId);
    }

    public void validateUpdateMateriaPrima(String nome, TipoMateriaPrima tipo, String unidade,
                                           BigDecimal stockMinimo,
                                           BigDecimal precoUnitario, UUID fornecedorId) {
        validarNome(nome);
        validarTipo(tipo);
        validarUnidade(unidade);
        validarStockMinimo(stockMinimo);
        validarPrecoUnitario(precoUnitario);
        validarFornecedor(fornecedorId);
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (nome.length() < 2 || nome.length() > 120) {
            throw new IllegalArgumentException("Nome deve ter entre 2 e 120 caracteres");
        }
        if (materiaPrimaRepository.existsByNomeIgnoreCase(nome)) {
            throw new IllegalArgumentException("Já existe uma matéria prima com este nome");
        }
    }

    private void validarTipo(TipoMateriaPrima tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de matéria prima é obrigatório");
        }
    }

    private void validarUnidade(String unidade) {
        if (unidade != null && unidade.length() > 3) {
            throw new IllegalArgumentException("Unidade não pode ter mais de 3 caracteres");
        }
    }

    private void validarStockAtual(BigDecimal stockAtual) {
        if (stockAtual != null && stockAtual.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Stock atual não pode ser negativo");
        }
    }

    private void validarStockMinimo(BigDecimal stockMinimo) {
        if (stockMinimo != null && stockMinimo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Stock mínimo não pode ser negativo");
        }
    }

    private void validarPrecoUnitario(BigDecimal precoUnitario) {
        if (precoUnitario == null || precoUnitario.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço unitário é obrigatório e não pode ser negativo");
        }
    }

    private void validarFornecedor(UUID fornecedorId) {
        if (fornecedorId == null) {
            throw new IllegalArgumentException("Fornecedor é obrigatório!");
        }
        if (!fornecedorRepository.existsById(fornecedorId)) {
            throw new IllegalArgumentException("Fornecedor não encontrado!");
        }
    }
}