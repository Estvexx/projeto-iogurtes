package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ordem_producao_produtos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"ordem_id", "produto_id"}))
public class OrdemProducaoProduto extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_id", nullable = false)
    private OrdemProducao ordem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoFinal produto;

    @Column(name = "quantidade_kg", nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidadeKg;

    public OrdemProducaoProduto() {}

    public OrdemProducaoProduto(OrdemProducao ordem, ProdutoFinal produto, BigDecimal quantidadeKg) {
        this.ordem = ordem;
        this.produto = produto;
        this.quantidadeKg = quantidadeKg;
    }

    // Getters
    public OrdemProducao getOrdem() { return ordem; }
    public ProdutoFinal getProduto() { return produto; }
    public BigDecimal getQuantidadeKg() { return quantidadeKg; }

    // Setters
    public void setQuantidadeKg(BigDecimal quantidadeKg) { this.quantidadeKg = quantidadeKg; }
}