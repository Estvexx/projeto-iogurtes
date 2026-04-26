package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.model.MateriaPrima;
import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoMP;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.MateriaPrimaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class MovimentoStockMPValidator {

    private final UserRepository userRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;

    public MovimentoStockMPValidator(UserRepository userRepository,
                                     MateriaPrimaRepository materiaPrimaRepository) {
        this.userRepository = userRepository;
        this.materiaPrimaRepository = materiaPrimaRepository;
    }

    public void validateMovimento(UUID userId, UUID materiaId,
                                  TipoMovimentoMP tipo, BigDecimal quantidade) {
        validarUser(userId);
        validarMateria(materiaId);
        validarQuantidade(quantidade);
        validarSaida(tipo, quantidade, materiaId);
    }

    private void validarUser(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User é obrigatório!");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User não encontrado!"));

      /*  boolean temPermissao = user.getRoles().stream()
                .anyMatch(r -> r.getRole() == UserRoleType.FUNCIONARIO
                        || r.getRole() == UserRoleType.ADMIN);

        if (!temPermissao) {
            throw new IllegalArgumentException("Apenas FUNCIONARIO ou ADMIN podem registar movimentos!");
        }
        */
    }



    private void validarMateria(UUID materiaId) {
        if (materiaId == null) {
            throw new IllegalArgumentException("Matéria prima é obrigatória!");
        }
        if (!materiaPrimaRepository.existsById(materiaId)) {
            throw new IllegalArgumentException("Matéria prima não encontrada!");
        }
    }

    private void validarQuantidade(BigDecimal quantidade) {
        if (quantidade == null || quantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantidade tem de ser positiva!");
        }
    }

    private void validarSaida(TipoMovimentoMP tipo, BigDecimal quantidade, UUID materiaId) {
        if (tipo == TipoMovimentoMP.SAIDA) {
            MateriaPrima materia = materiaPrimaRepository.findById(materiaId)
                    .orElseThrow(() -> new IllegalArgumentException("Matéria prima não encontrada!"));

            if (materia.getStockAtual().compareTo(quantidade) < 0) {
                throw new IllegalArgumentException("Stock insuficiente! Stock atual: "
                        + materia.getStockAtual() + " | Quantidade pedida: " + quantidade);
            }
        }
    }
}