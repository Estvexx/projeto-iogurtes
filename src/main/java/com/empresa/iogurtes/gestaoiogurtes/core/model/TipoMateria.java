package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipos_materia")
public class TipoMateria extends BaseEntity {

    @Column(name = "nome", nullable = false, unique = true, length = 80)
    private String nome;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @Column(name = "taxa_iva", nullable = false, precision = 5, scale = 2)
    private BigDecimal taxaIva = new BigDecimal("23.00");

    @OneToMany(mappedBy = "tipo")
    private List<MateriaPrima> materiasPrimas = new ArrayList<>();

    public TipoMateria() {}

    public TipoMateria(String nome, String descricao, BigDecimal taxaIva) {
        this.nome = nome;
        this.descricao = descricao;
        this.taxaIva = taxaIva;
    }

    // Getters
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public BigDecimal getTaxaIva() { return taxaIva; }
    public List<MateriaPrima> getMateriasPrimas() { return materiasPrimas; }

    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setTaxaIva(BigDecimal taxaIva) { this.taxaIva = taxaIva; }
    public void setMateriasPrimas(List<MateriaPrima> materiasPrimas) { this.materiasPrimas = materiasPrimas; }
}