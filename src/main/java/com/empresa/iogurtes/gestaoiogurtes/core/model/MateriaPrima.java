package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "materias_primas")
public class MateriaPrima extends BaseEntity {

    @Column(name = "nome", nullable = false, unique = true, length = 120)
    private String nome;

    @Column(name = "unidade", length = 10)
    private String unidade;

    @Column(name = "stock_atual", precision = 12, scale = 3)
    private BigDecimal stockAtual = BigDecimal.ZERO;

    @Column(name = "stock_minimo", precision = 12, scale = 3)
    private BigDecimal stockMinimo = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_id")
    private TipoMateria tipo;

    public MateriaPrima() {}

    public MateriaPrima(String nome, String unidade, BigDecimal stockMinimo,
                        TipoMateria tipo) {
        this.nome = nome;
        this.unidade = unidade;
        this.stockAtual = BigDecimal.ZERO;
        this.stockMinimo = stockMinimo;
        this.tipo = tipo;
    }

    // Getters
    public String getNome() { return nome; }
    public String getUnidade() { return unidade; }
    public BigDecimal getStockAtual() { return stockAtual; }
    public BigDecimal getStockMinimo() { return stockMinimo; }
    public TipoMateria getTipo() { return tipo; }

    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setUnidade(String unidade) { this.unidade = unidade; }
    public void setStockAtual(BigDecimal stockAtual) { this.stockAtual = stockAtual; }
    public void setStockMinimo(BigDecimal stockMinimo) { this.stockMinimo = stockMinimo; }
    public void setTipo(TipoMateria tipo) { this.tipo = tipo; }
}