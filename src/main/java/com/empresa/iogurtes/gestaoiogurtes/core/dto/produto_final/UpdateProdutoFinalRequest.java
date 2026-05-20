package com.empresa.iogurtes.gestaoiogurtes.core.dto.produto_final;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoFisico;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateProdutoFinalRequest(

        @NotBlank(message = "Nome do produto é obrigatório")
        @Size(min = 2, max = 120, message = "Nome deve ter entre 2 e 120 caracteres")
        String nome,

        String descricao,

        @NotBlank(message = "Abreviação do sabor é obrigatória")
        @Size(min = 3, max = 3, message = "Abreviação do sabor deve ter exatamente 3 letras")
        @Pattern(regexp = "^[A-Za-z]{3}$", message = "Abreviação deve conter apenas letras (ex: MOR, BAU)")
        String abreviacaoSabor,

        @NotNull(message = "Estado físico é obrigatório")
        EstadoFisico estadoFisico,

        @Min(value = 1, message = "Validade deve ser pelo menos 1 dia")
        Integer validadeDias,

        @DecimalMin(value = "0.01", message = "Preço de venda deve ser maior que zero")
        @Digits(integer = 8, fraction = 2, message = "Preço de venda inválido")
        BigDecimal precoVenda,

        @DecimalMin(value = "0.01", message = "Preço por kg deve ser maior que zero")
        @Digits(integer = 8, fraction = 2, message = "Preço por kg inválido")
        BigDecimal precoPorKg,

        @NotNull(message = "Taxa de IVA é obrigatória")
        @DecimalMin(value = "0.0", message = "Taxa de IVA não pode ser negativa")
        @Digits(integer = 3, fraction = 2, message = "Taxa de IVA inválida")
        BigDecimal taxaIva,

        @NotNull(message = "Visível ao cliente é obrigatório")
        boolean visivelCliente,

        @NotNull(message = "Quantidade por lote é obrigatória")
        @Min(value = 1, message = "Quantidade por lote deve ser pelo menos 1")
        Integer quantidadeLote
) {}