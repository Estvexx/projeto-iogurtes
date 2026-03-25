package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "ordem_producao_produtos")
public class OrdemProducaoProduto extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "ordem_id", nullable = false)
    private OrdemProducao ordem;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoFinal produto;

    @Column(name = "quantidade_kg", nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidadeKg;

    public OrdemProducaoProduto() {}

    // construtor com objeto completo
    public OrdemProducaoProduto(OrdemProducao ordem, ProdutoFinal produto, BigDecimal quantidadeKg) {
        this.ordem = ordem;
        this.produto = produto;
        this.quantidadeKg = quantidadeKg;
    }

    // construtor só com o id do produto
    public OrdemProducaoProduto(OrdemProducao ordem, UUID produtoId, BigDecimal quantidadeKg) {
        this.ordem = ordem;
        this.produto = new ProdutoFinal();
        this.produto.setId(produtoId);
        this.quantidadeKg = quantidadeKg;
    }

    public OrdemProducao getOrdem() { return ordem; }
    public ProdutoFinal getProduto() { return produto; }
    public BigDecimal getQuantidadeKg() { return quantidadeKg; }

    public void setOrdem(OrdemProducao ordem) { this.ordem = ordem; }
    public void setProduto(ProdutoFinal produto) { this.produto = produto; }
    public void setQuantidadeKg(BigDecimal quantidadeKg) { this.quantidadeKg = quantidadeKg; }

    @Override
    public String toString() {
        return "OrdemProducaoProduto{" +
                "id=" + getId() +
                ", produto=" + produto.getNome() +
                ", quantidadeKg=" + quantidadeKg +
                '}';
    }
}