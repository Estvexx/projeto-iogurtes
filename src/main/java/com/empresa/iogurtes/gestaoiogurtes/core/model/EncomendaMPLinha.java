package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "encomenda_mp_linhas",
        uniqueConstraints = @UniqueConstraint(columnNames = {"encomenda_mp_id", "materia_id"}))
public class EncomendaMPLinha extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encomenda_mp_id", nullable = false)
    private EncomendaMP encomenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id", nullable = false)
    private MateriaPrima materia;

    @Column(name = "quantidade", nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidade;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @Column(name = "preco_unitario_eur", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitarioEur;

    @Column(name = "taxa_iva", nullable = false, precision = 5, scale = 2)
    private BigDecimal taxaIva;

    public EncomendaMPLinha() {}

    public EncomendaMPLinha(EncomendaMP encomenda, MateriaPrima materia, BigDecimal quantidade,
                            BigDecimal precoUnitario, BigDecimal precoUnitarioEur, BigDecimal taxaIva) {
        this.encomenda = encomenda;
        this.materia = materia;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.precoUnitarioEur = precoUnitarioEur;
        this.taxaIva = taxaIva;
    }

    // Getters
    public EncomendaMP getEncomenda() { return encomenda; }
    public MateriaPrima getMateria() { return materia; }
    public BigDecimal getQuantidade() { return quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public BigDecimal getPrecoUnitarioEur() { return precoUnitarioEur; }
    public BigDecimal getTaxaIva() { return taxaIva; }

    // Setters
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
    public void setPrecoUnitarioEur(BigDecimal precoUnitarioEur) { this.precoUnitarioEur = precoUnitarioEur; }
    public void setTaxaIva(BigDecimal taxaIva) { this.taxaIva = taxaIva; }
    public void setEncomenda(EncomendaMP encomenda) { this.encomenda = encomenda; }
}
