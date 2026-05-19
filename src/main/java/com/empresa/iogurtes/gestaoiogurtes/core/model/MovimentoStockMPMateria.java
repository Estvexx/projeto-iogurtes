package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "movimento_stock_mp_materias",
        uniqueConstraints = @UniqueConstraint(columnNames = {"movimento_id", "materia_id"}))
public class MovimentoStockMPMateria extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movimento_id", nullable = false)
    private MovimentoStockMP movimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id", nullable = false)
    private MateriaPrima materia;

    @Column(name = "quantidade", nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidade;

    public MovimentoStockMPMateria() {}

    public MovimentoStockMPMateria(MovimentoStockMP movimento, MateriaPrima materia, BigDecimal quantidade) {
        this.movimento = movimento;
        this.materia = materia;
        this.quantidade = quantidade;
    }

    // Getters
    public MovimentoStockMP getMovimento() { return movimento; }
    public MateriaPrima getMateria() { return materia; }
    public BigDecimal getQuantidade() { return quantidade; }

    // Setters
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }
}
