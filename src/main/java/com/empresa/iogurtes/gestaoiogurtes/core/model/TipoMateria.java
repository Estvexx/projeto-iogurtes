package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipos_materia")
public class TipoMateria extends BaseEntity {

    @Column(name = "nome", nullable = false, unique = true, length = 80)
    private String nome;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @OneToMany(mappedBy = "tipo")
    private List<MateriaPrima> materiasPrimas = new ArrayList<>();

    public TipoMateria() {}

    public TipoMateria(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public List<MateriaPrima> getMateriasPrimas() { return materiasPrimas; }

    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setMateriasPrimas(List<MateriaPrima> materiasPrimas) { this.materiasPrimas = materiasPrimas; }
}