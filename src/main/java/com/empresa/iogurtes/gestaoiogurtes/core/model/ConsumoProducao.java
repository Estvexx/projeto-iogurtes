package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "consumos_producao",
        uniqueConstraints = @UniqueConstraint(columnNames = {"ordem_id", "materia_id"}))
public class ConsumoProducao extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_id", nullable = false)
    private OrdemProducao ordem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id", nullable = false)
    private MateriaPrima materia;

    @Column(name = "quantidade_kg", nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidadeKg;

    public ConsumoProducao() {}

    public ConsumoProducao(OrdemProducao ordem, MateriaPrima materia, BigDecimal quantidadeKg) {
        this.ordem = ordem;
        this.materia = materia;
        this.quantidadeKg = quantidadeKg;
    }

    // Getters
    public OrdemProducao getOrdem() { return ordem; }
    public MateriaPrima getMateria() { return materia; }
    public BigDecimal getQuantidadeKg() { return quantidadeKg; }

    // Setters
    public void setQuantidadeKg(BigDecimal quantidadeKg) { this.quantidadeKg = quantidadeKg; }
}