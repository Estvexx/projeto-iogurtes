package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "moedas")
public class Moeda extends BaseEntity {

    @Column(name = "codigo", nullable = false, unique = true, length = 3)
    private String codigo;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "simbolo", nullable = false, length = 5)
    private String simbolo;

    @Column(name = "taxa_conversao_eur", nullable = false, precision = 12, scale = 6)
    private BigDecimal taxaConversaoEur;

    public Moeda() {}

    public Moeda(String codigo, String nome, String simbolo, BigDecimal taxaConversaoEur) {
        this.codigo = codigo.toUpperCase();
        this.nome = nome;
        this.simbolo = simbolo;
        this.taxaConversaoEur = taxaConversaoEur;
    }

    // Getters
    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public String getSimbolo() { return simbolo; }
    public BigDecimal getTaxaConversaoEur() { return taxaConversaoEur; }

    // Setters
    public void setCodigo(String codigo) { this.codigo = codigo.toUpperCase(); }
    public void setNome(String nome) { this.nome = nome; }
    public void setSimbolo(String simbolo) { this.simbolo = simbolo; }
    public void setTaxaConversaoEur(BigDecimal taxaConversaoEur) { this.taxaConversaoEur = taxaConversaoEur; }
}