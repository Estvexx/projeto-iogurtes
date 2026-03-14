package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "consumos_producao",
        uniqueConstraints = @UniqueConstraint(columnNames = {"ordem_id", "materia_id"}))

public class ConsumoProducao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ordem_id", nullable = false)
    private OrdemProducao ordem;

    @ManyToOne
    @JoinColumn(name = "materia_id", nullable = false)
    private MateriaPrima materia;

    @Column(name = "quantidade_kg", nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidadeKg;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    public ConsumoProducao() {}

    public ConsumoProducao(OrdemProducao ordem, MateriaPrima materia,
                           BigDecimal quantidadeKg) {
        this.ordem = ordem;
        this.materia = materia;
        this.quantidadeKg = quantidadeKg;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public OrdemProducao getOrdem() { return ordem; }
    public MateriaPrima getMateria() { return materia; }
    public BigDecimal getQuantidadeKg() { return quantidadeKg; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(UUID id) { this.id = id; }
    public void setOrdem(OrdemProducao ordem) { this.ordem = ordem; }
    public void setMateria(MateriaPrima materia) { this.materia = materia; }
    public void setQuantidadeKg(BigDecimal quantidadeKg) { this.quantidadeKg = quantidadeKg; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "ConsumoProducao{" +
                "id=" + id +
                ", materia=" + materia.getNome() +
                ", quantidadeKg=" + quantidadeKg +
                '}';
    }
}