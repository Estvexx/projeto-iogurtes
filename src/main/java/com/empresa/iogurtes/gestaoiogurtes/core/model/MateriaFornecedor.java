package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "materia_fornecedores",
        uniqueConstraints = @UniqueConstraint(columnNames = {"materia_id", "fornecedor_id"}))
public class MateriaFornecedor extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id", nullable = false)
    private MateriaPrima materia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moeda_id", nullable = false)
    private Moeda moeda;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @Column(name = "preco_unitario_eur", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitarioEur;

    @Column(name = "prazo_estimado_entrega_dias")
    private Integer prazoEstimadoEntregaDias;

    @Column(name = "preferencial", nullable = false)
    private boolean preferencial = false;

    public MateriaFornecedor() {}

    public MateriaFornecedor(MateriaPrima materia, Fornecedor fornecedor, Moeda moeda,
                             BigDecimal precoUnitario, BigDecimal precoUnitarioEur,
                             Integer prazoEstimadoEntregaDias, boolean preferencial) {
        this.materia = materia;
        this.fornecedor = fornecedor;
        this.moeda = moeda;
        this.precoUnitario = precoUnitario;
        this.precoUnitarioEur = precoUnitarioEur;
        this.prazoEstimadoEntregaDias = prazoEstimadoEntregaDias;
        this.preferencial = preferencial;
    }

    // Getters
    public MateriaPrima getMateria() { return materia; }
    public Fornecedor getFornecedor() { return fornecedor; }
    public Moeda getMoeda() { return moeda; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public BigDecimal getPrecoUnitarioEur() { return precoUnitarioEur; }
    public Integer getPrazoEstimadoEntregaDias() { return prazoEstimadoEntregaDias; }
    public boolean isPreferencial() { return preferencial; }

    // Setters
    public void setMateria(MateriaPrima materia) { this.materia = materia; }
    public void setFornecedor(Fornecedor fornecedor) { this.fornecedor = fornecedor; }
    public void setMoeda(Moeda moeda) { this.moeda = moeda; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
    public void setPrecoUnitarioEur(BigDecimal precoUnitarioEur) { this.precoUnitarioEur = precoUnitarioEur; }
    public void setPrazoEstimadoEntregaDias(Integer prazoEstimadoEntregaDias) { this.prazoEstimadoEntregaDias = prazoEstimadoEntregaDias; }
    public void setPreferencial(boolean preferencial) { this.preferencial = preferencial; }
}