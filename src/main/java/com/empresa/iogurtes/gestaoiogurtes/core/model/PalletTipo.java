package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pallet_tipos")
public class PalletTipo extends BaseEntity {

    @Column(name = "nome", nullable = false, length = 80)
    private String nome;

    @Column(name = "capacidade_kg", nullable = false, precision = 10, scale = 3)
    private BigDecimal capacidadeKg;

    public PalletTipo() {}

    public PalletTipo(String nome, BigDecimal capacidadeKg) {
        this.nome = nome;
        this.capacidadeKg = capacidadeKg;
    }

    public String getNome() { return nome; }
    public BigDecimal getCapacidadeKg() { return capacidadeKg; }

    public void setNome(String nome) { this.nome = nome; }
    public void setCapacidadeKg(BigDecimal capacidadeKg) { this.capacidadeKg = capacidadeKg; }

    @Override
    public String toString() {
        return "PalletTipo{" +
                "id=" + getId() +
                ", nome='" + nome + '\'' +
                ", capacidadeKg=" + capacidadeKg +
                '}';
    }
}