package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.MateriaPrima;
import com.empresa.iogurtes.gestaoiogurtes.core.model.MovimentoStockMP;
import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoMP;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MateriaPrimaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MovimentoStockMPRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.MovimentoStockMPValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class MovimentoStockMPService {

    private final MovimentoStockMPRepository movimentoRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;
    private final UserRepository userRepository;
    private final MovimentoStockMPValidator validator;

    public MovimentoStockMPService(MovimentoStockMPRepository movimentoRepository,
                                   MateriaPrimaRepository materiaPrimaRepository,
                                   UserRepository userRepository,
                                   MovimentoStockMPValidator validator) {
        this.movimentoRepository = movimentoRepository;
        this.materiaPrimaRepository = materiaPrimaRepository;
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Transactional
    public MovimentoStockMP registarMovimento(UUID userId, UUID materiaId,
                                              TipoMovimentoMP tipo, BigDecimal quantidade,
                                              String observacao) {

        validator.validateMovimento(userId, materiaId, tipo, quantidade);

        User user = userRepository.getReferenceById(userId);
        MateriaPrima materia = materiaPrimaRepository.findById(materiaId)
                .orElseThrow(() -> new IllegalArgumentException("Matéria prima não encontrada!"));

        // atualiza o stock consoante o tipo de movimento
        switch (tipo) {
            case ENTRADA -> materia.setStockAtual(materia.getStockAtual().add(quantidade));
            case SAIDA   -> materia.setStockAtual(materia.getStockAtual().subtract(quantidade));
            case AJUSTE  -> materia.setStockAtual(quantidade); // ajuste define o valor diretamente
        }

        materiaPrimaRepository.save(materia);

        MovimentoStockMP movimento = new MovimentoStockMP(user, materia, tipo, quantidade, observacao);
        return movimentoRepository.save(movimento);
    }

    public List<MovimentoStockMP> getByMateria(UUID materiaId) {
        return movimentoRepository.findByMateriaId(materiaId);
    }

    public List<MovimentoStockMP> getByUser(UUID userId) {
        return movimentoRepository.findByUserId(userId);
    }

    public List<MovimentoStockMP> getAll() {
        return movimentoRepository.findAllByIsActiveTrue();
    }

    public List<MovimentoStockMP> getAllIncludingInactive() {
        return movimentoRepository.findAll();
    }
}