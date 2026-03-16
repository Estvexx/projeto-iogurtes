package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Fornecedor;
import com.empresa.iogurtes.gestaoiogurtes.core.model.MateriaPrima;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMateriaPrima;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.FornecedorRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MateriaPrimaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.MateriaPrimaValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class MateriaPrimaService {

    private final MateriaPrimaRepository materiaPrimaRepository;
    private final MateriaPrimaValidator materiaPrimaValidator;
    private final FornecedorRepository fornecedorRepository;

    public MateriaPrimaService(MateriaPrimaRepository materiaPrimaRepository,
                               MateriaPrimaValidator materiaPrimaValidator,
                               FornecedorRepository fornecedorRepository) {
        this.materiaPrimaRepository = materiaPrimaRepository;
        this.materiaPrimaValidator = materiaPrimaValidator;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public MateriaPrima createMateriaPrima(String nome, String unidade,
                                           TipoMateriaPrima tipo,
                                           BigDecimal stockAtual, BigDecimal stockMinimo,
                                           BigDecimal precoUnitario, UUID fornecedorId) {

        materiaPrimaValidator.validateCreateMateriaPrima(nome, tipo, unidade, stockAtual, stockMinimo, precoUnitario, fornecedorId);

        Fornecedor fornecedor = fornecedorRepository.getReferenceById(fornecedorId);

        MateriaPrima materiaPrima = new MateriaPrima(nome, tipo, unidade, stockAtual, stockMinimo, precoUnitario, fornecedor);
        return materiaPrimaRepository.save(materiaPrima);
    }

    @Transactional
    public MateriaPrima updateMateriaPrima(UUID id, String nome, String unidade,
                                           TipoMateriaPrima tipo,
                                           BigDecimal stockMinimo, BigDecimal precoUnitario,
                                           UUID fornecedorId) {

        MateriaPrima materiaPrima = materiaPrimaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matéria prima não encontrada"));

        materiaPrimaValidator.validateUpdateMateriaPrima(nome,tipo,  unidade, stockMinimo, precoUnitario, fornecedorId);

        Fornecedor fornecedor = fornecedorRepository.getReferenceById(fornecedorId);

        materiaPrima.setNome(nome);
        materiaPrima.setUnidade(unidade);
        materiaPrima.setTipo(tipo);
        materiaPrima.setStockMinimo(stockMinimo);
        materiaPrima.setPrecoUnitario(precoUnitario);
        materiaPrima.setFornecedor(fornecedor);

        return materiaPrimaRepository.save(materiaPrima);
    }

    public MateriaPrima getById(UUID id) {
        return materiaPrimaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matéria prima não encontrada"));
    }

    public List<MateriaPrima> getAll() {
        return materiaPrimaRepository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        MateriaPrima materiaPrima = materiaPrimaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matéria prima não encontrada"));

        materiaPrimaRepository.delete(materiaPrima);
    }
}