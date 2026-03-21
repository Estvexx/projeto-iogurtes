package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.repository.PalletTipoRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PalletTipoValidator {

    private final PalletTipoRepository palletTipoRepository;

    public PalletTipoValidator(PalletTipoRepository palletTipoRepository) {
        this.palletTipoRepository = palletTipoRepository;
    }

    public void validarCreate(String nome, BigDecimal capacidadeKg) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");

        if (nome.length() > 80)
            throw new IllegalArgumentException("Nome não pode exceder 80 caracteres");

        if (palletTipoRepository.existsByNome(nome))
            throw new IllegalArgumentException("Já existe um tipo de pallet com este nome");

        validarCapacidade(capacidadeKg);
    }

    public void validarUpdate(String nome, BigDecimal capacidadeKg) {
        if (nome != null && nome.isBlank())
            throw new IllegalArgumentException("Nome não pode ser vazio se fornecido");

        if (nome != null && nome.length() > 80)
            throw new IllegalArgumentException("Nome não pode exceder 80 caracteres");

        if (nome != null && palletTipoRepository.existsByNome(nome))
            throw new IllegalArgumentException("Já existe um tipo de pallet com este nome");

        if (capacidadeKg != null) validarCapacidade(capacidadeKg);
    }

    private void validarCapacidade(BigDecimal capacidadeKg) {
        if (capacidadeKg == null)
            throw new IllegalArgumentException("Capacidade em kg é obrigatória");

        if (capacidadeKg.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Capacidade deve ser maior que zero");
    }
}