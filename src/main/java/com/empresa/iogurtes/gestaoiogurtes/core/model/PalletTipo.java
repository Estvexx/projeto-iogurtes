package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pallet_tipos")
public class PalletTipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 80)
    private String nome;

    @Column(name = "capacidade_kg", nullable = false, precision = 10, scale = 3)
    private BigDecimal capacidadeKg;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "updatedat")
    private LocalDateTime updatedAt;

    public PalletTipo() {}

    public PalletTipo(String nome, BigDecimal capacidadeKg) {
        this.nome = nome;
        this.capacidadeKg = capacidadeKg;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getCapacidadeKg() { return capacidadeKg; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Integer id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCapacidadeKg(BigDecimal capacidadeKg) { this.capacidadeKg = capacidadeKg; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "PalletTipo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", capacidadeKg=" + capacidadeKg +
                '}';
    }
}