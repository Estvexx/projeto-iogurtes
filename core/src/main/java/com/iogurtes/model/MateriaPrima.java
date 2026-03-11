package com.iogurtes.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "materias_primas")
public class MateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "unidade", length = 10)
    private String unidade;

    @Column(name = "stock_atual", precision = 12, scale = 3)
    private BigDecimal stockAtual = BigDecimal.ZERO;

    @Column(name = "stock_minimo", precision = 12, scale = 3)
    private BigDecimal stockMinimo = BigDecimal.ZERO;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "updatedat")
    private LocalDateTime updatedAt;

    public MateriaPrima() {}

    // utilizo o bigdecimal devido a precisão dos valores
    public MateriaPrima(String nome, String unidade, BigDecimal stockAtual,
                        BigDecimal stockMinimo, BigDecimal precoUnitario,
                        Fornecedor fornecedor) {
        this.nome = nome;
        this.unidade = unidade;
        this.stockAtual = stockAtual;
        this.stockMinimo = stockMinimo;
        this.precoUnitario = precoUnitario;
        this.fornecedor = fornecedor;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public String getUnidade() { return unidade; }
    public BigDecimal getStockAtual() { return stockAtual; }
    public BigDecimal getStockMinimo() { return stockMinimo; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public Fornecedor getFornecedor() { return fornecedor; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Integer id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setUnidade(String unidade) { this.unidade = unidade; }
    public void setStockAtual(BigDecimal stockAtual) { this.stockAtual = stockAtual; }
    public void setStockMinimo(BigDecimal stockMinimo) { this.stockMinimo = stockMinimo; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
    public void setFornecedor(Fornecedor fornecedor) { this.fornecedor = fornecedor; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "MateriaPrima{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", stockAtual=" + stockAtual +
                ", stockMinimo=" + stockMinimo +
                '}';
    }
}