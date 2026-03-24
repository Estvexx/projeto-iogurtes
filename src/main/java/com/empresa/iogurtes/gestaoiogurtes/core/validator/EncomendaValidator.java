package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.model.EncomendaPallet;
import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EncomendaValidator {

    public void validarCreate(UUID userId, User user, List<EncomendaPallet> pallets) {

        if (userId == null)
            throw new IllegalArgumentException("Utilizador é obrigatório");

        // só users com role CLIENTE podem fazer encomendas
        boolean isCliente = user.getRoles().stream()
                .anyMatch(r -> r.getRole() == UserRoleType.EMPRESA);

        if (!isCliente)
            throw new IllegalStateException("Apenas clientes podem realizar encomendas");

        if (pallets == null || pallets.isEmpty())
            throw new IllegalArgumentException("A encomenda deve ter pelo menos um pallet");

        for (EncomendaPallet ep : pallets) {
            if (ep.getProduto() == null || ep.getProduto().getId() == null)
                throw new IllegalArgumentException("Produto é obrigatório em cada linha");

            if (ep.getPalletTipo() == null || ep.getPalletTipo().getId() == null)
                throw new IllegalArgumentException("Tipo de pallet é obrigatório em cada linha");

            if (ep.getQuantidadePallets() == null || ep.getQuantidadePallets() <= 0)
                throw new IllegalArgumentException("Quantidade de pallets deve ser maior que zero");

            if (ep.getPrecoPorPallet() == null || ep.getPrecoPorPallet().compareTo(java.math.BigDecimal.ZERO) <= 0)
                throw new IllegalArgumentException("Preço por pallet deve ser maior que zero");
        }
    }
}