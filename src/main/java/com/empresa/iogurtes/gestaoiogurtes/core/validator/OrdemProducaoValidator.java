package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.model.OrdemProducao;
import com.empresa.iogurtes.gestaoiogurtes.core.model.OrdemProducaoProduto;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoOrdem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;



@Component
public class OrdemProducaoValidator {

    public void validarCreate(UUID userId, LocalDateTime dataInicio, LocalDateTime dataFim,
                              String observacoes, List<OrdemProducaoProduto> produtos) {

        if (userId == null)
            throw new IllegalArgumentException("Utilizador é obrigatório");

        if (dataInicio == null)
            throw new IllegalArgumentException("Data de início é obrigatória");

        if (dataFim == null)
            throw new IllegalArgumentException("Data de fim é obrigatória");

        if (dataInicio.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Data de início não pode ser no passado");

        if (dataFim.isBefore(dataInicio))
            throw new IllegalArgumentException("Data de fim não pode ser anterior à data de início");

        if (dataFim.isEqual(dataInicio))
            throw new IllegalArgumentException("Data de fim não pode ser igual à data de início");

        if (observacoes != null && observacoes.isBlank())
            throw new IllegalArgumentException("Observações não podem ser vazias se fornecidas");

        if (observacoes != null && observacoes.length() > 500)
            throw new IllegalArgumentException("Observações não podem exceder 500 caracteres");

        if (produtos == null || produtos.isEmpty())
            throw new IllegalArgumentException("A ordem deve ter pelo menos um produto");

        for (OrdemProducaoProduto opp : produtos) {
            if (opp.getProduto() == null || opp.getProduto().getId() == null)
                throw new IllegalArgumentException("Produto é obrigatório em cada linha");
            if (opp.getQuantidadeKg() == null || opp.getQuantidadeKg().compareTo(BigDecimal.ZERO) <= 0)
                throw new IllegalArgumentException("Quantidade deve ser maior que zero em cada linha");
        }
    }

    public void validarUpdate(OrdemProducao ordem, LocalDateTime dataInicio,
                              LocalDateTime dataFim, String observacoes) {

        if (ordem.getEstado() == EstadoOrdem.CANCELADA)
            throw new IllegalStateException("Não é possível editar uma ordem cancelada");

        if (ordem.getEstado() == EstadoOrdem.CONCLUIDA)
            throw new IllegalStateException("Não é possível editar uma ordem já concluída");

        // só podes editar se a dataFim atual ainda não passou
        if (ordem.getDataFim().isBefore(LocalDateTime.now()))
            throw new IllegalStateException("Não é possível editar uma ordem cuja data de fim já passou");

        LocalDateTime inicio = dataInicio != null ? dataInicio : ordem.getDataInicio();
        LocalDateTime fim = dataFim != null ? dataFim : ordem.getDataFim();

        if (fim.isBefore(inicio) || fim.isEqual(inicio))
            throw new IllegalArgumentException("Data de fim deve ser posterior à data de início");

        if (observacoes != null && observacoes.isBlank())
            throw new IllegalArgumentException("Observações não podem ser vazias se fornecidas");

        if (observacoes != null && observacoes.length() > 500)
            throw new IllegalArgumentException("Observações não podem exceder 500 caracteres");
    }

    public void validarCancelamento(OrdemProducao ordem) {
        if (ordem.getEstado() == EstadoOrdem.CONCLUIDA)
            throw new IllegalStateException("Não é possível cancelar uma ordem já concluída");

        if (ordem.getEstado() == EstadoOrdem.CANCELADA)
            throw new IllegalStateException("Ordem já se encontra cancelada");
    }
}