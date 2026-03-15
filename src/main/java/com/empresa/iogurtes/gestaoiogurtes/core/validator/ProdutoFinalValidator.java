package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.model.ProdutoMateria;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.ProdutoFinalRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class ProdutoFinalValidator {

    private final ProdutoFinalRepository produtoFinalRepository;

    public ProdutoFinalValidator(ProdutoFinalRepository produtoFinalRepository) {
        this.produtoFinalRepository = produtoFinalRepository;
    }

    public void validateCreateProduto(String codigoSku, String nome, BigDecimal precoVenda,
                                      BigDecimal precoPorKg, Integer quantidadeLote,
                                      List<ProdutoMateria> materias) {
        validarCodigoSku(codigoSku, null);   // ✅ null = é create
        validarNome(nome, null);              // ✅ null = é create
        validarPrecoVenda(precoVenda);
        validarPrecoPorKg(precoPorKg);
        validarQuantidadeLote(quantidadeLote);
        validarMaterias(materias);
    }

    public void validateUpdateProduto(UUID id, String nome, BigDecimal precoVenda,
                                      BigDecimal precoPorKg, Integer quantidadeLote) {
        validarNome(nome, id);               // ✅ passa o id para excluir o próprio
        validarPrecoVenda(precoVenda);
        validarPrecoPorKg(precoPorKg);
        validarQuantidadeLote(quantidadeLote);
    }

    private void validarCodigoSku(String codigoSku, UUID id) {
        if (codigoSku == null || codigoSku.isBlank()) {
            throw new IllegalArgumentException("Código SKU é obrigatório!");
        }
        if (codigoSku.length() > 50) {
            throw new IllegalArgumentException("Código SKU não pode ter mais de 50 caracteres!");
        }
        boolean existe = id == null
                ? produtoFinalRepository.existsByCodigoSku(codigoSku)
                : produtoFinalRepository.existsByCodigoSkuAndIdNot(codigoSku, id);

        if (existe) {
            throw new IllegalArgumentException("Já existe um produto com este SKU!");
        }
    }

    private void validarNome(String nome, UUID id) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório!");
        }
        if (nome.length() < 2 || nome.length() > 120) {
            throw new IllegalArgumentException("Nome deve ter entre 2 e 120 caracteres!");
        }
        boolean existe = id == null
                ? produtoFinalRepository.existsByNome(nome)
                : produtoFinalRepository.existsByNomeAndIdNot(nome, id);

        if (existe) {
            throw new IllegalArgumentException("Já existe um produto com este nome!");
        }
    }

    private void validarPrecoVenda(BigDecimal precoVenda) {
        if (precoVenda == null || precoVenda.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço de venda é obrigatório e não pode ser negativo!");
        }
    }

    private void validarPrecoPorKg(BigDecimal precoPorKg) {
        if (precoPorKg == null || precoPorKg.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço por kg é obrigatório e não pode ser negativo!");
        }
    }

    private void validarQuantidadeLote(Integer quantidadeLote) {
        if (quantidadeLote == null || quantidadeLote <= 0) {
            throw new IllegalArgumentException("Quantidade de lote tem de ser positiva!");
        }
    }

    private void validarMaterias(List<ProdutoMateria> materias) {
        if (materias == null || materias.isEmpty()) {
            throw new IllegalArgumentException("Um produto deve ter pelo menos uma matéria prima na receita!");
        }

        for (ProdutoMateria materia : materias) {
            if (materia.getMateria() == null) {
                throw new IllegalArgumentException("Matéria prima é obrigatória na receita!");
            }
            if (materia.getQuantidadePorUnidadeProduto() == null
                    || materia.getQuantidadePorUnidadeProduto().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Quantidade da matéria prima na receita tem de ser positiva!");
            }
        }
    }
}