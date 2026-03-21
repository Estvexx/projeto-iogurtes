package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ordem_producao_produtos")
public class OrdemProducaoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ordem_id", nullable = false)
    private OrdemProducao ordem;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoFinal produto;

    @Column(name = "quantidade_kg", nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidadeKg;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
    }

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

    public UUID getId() { return id; }
    public OrdemProducao getOrdem() { return ordem; }
    public ProdutoFinal getProduto() { return produto; }
    public BigDecimal getQuantidadeKg() { return quantidadeKg; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(UUID id) { this.id = id; }
    public void setOrdem(OrdemProducao ordem) { this.ordem = ordem; }
    public void setProduto(ProdutoFinal produto) { this.produto = produto; }
    public void setQuantidadeKg(BigDecimal quantidadeKg) { this.quantidadeKg = quantidadeKg; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "OrdemProducaoProduto{" +
                "id=" + id +
                ", produto=" + produto.getNome() +
                ", quantidadeKg=" + quantidadeKg +
                '}';
    }
}