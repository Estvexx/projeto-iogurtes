package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fornecedor_tipos")
public class FornecedorTipo extends BaseEntity {

    @Column(name = "nome", unique = true, nullable = false, length = 80)
    private String nome;

    @Column(name = "descricao", length = 200)
    private String descricao;

    public FornecedorTipo() {}

    public FornecedorTipo(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }

    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() {
        return "FornecedorTipo{" +
                "id=" + getId() +
                ", nome='" + nome + '\'' +
                '}';
    }
}