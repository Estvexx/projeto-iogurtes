package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.PalletTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.PalletTipoRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.PalletTipoValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PalletTipoService {

    private final PalletTipoRepository palletTipoRepository;
    private final PalletTipoValidator validator;

    public PalletTipoService(PalletTipoRepository palletTipoRepository,
                             PalletTipoValidator validator) {
        this.palletTipoRepository = palletTipoRepository;
        this.validator = validator;
    }


    @Transactional
    public PalletTipo create(String nome, BigDecimal capacidadeKg) {
        validator.validarCreate(nome, capacidadeKg);
        return palletTipoRepository.save(new PalletTipo(nome, capacidadeKg));
    }

    public PalletTipo getById(UUID id) {
        return palletTipoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de pallet não encontrado"));
    }

    public List<PalletTipo> getAll() {
        return palletTipoRepository.findByIsActiveTrue();
    }

    @Transactional
    public PalletTipo update(UUID id, String nome, BigDecimal capacidadeKg) {
        PalletTipo palletTipo = getById(id);

        validator.validarUpdate(nome, capacidadeKg);

        if (nome != null) palletTipo.setNome(nome);
        if (capacidadeKg != null) palletTipo.setCapacidadeKg(capacidadeKg);

        return palletTipoRepository.save(palletTipo);
    }

    @Transactional
    public void delete(UUID id) {
        PalletTipo palletTipo = getById(id);
        palletTipo.setActive(false);
        palletTipoRepository.save(palletTipo);
    }
}