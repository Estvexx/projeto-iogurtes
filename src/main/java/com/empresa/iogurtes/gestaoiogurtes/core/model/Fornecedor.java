package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fornecedores")
public class Fornecedor extends BaseEntity {

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "nif", unique = true, length = 20)
    private String nif;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "morada", length = 200)
    private String morada;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private FornecedorTipo tipo;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FornecedorCertificacao> certificacoes = new ArrayList<>();

    public Fornecedor() {}

    public Fornecedor(String nome, String nif, String email, String telefone,
                      String morada, String cidade, FornecedorTipo tipo) {
        this.nome = nome;
        this.nif = nif;
        this.email = email;
        this.telefone = telefone;
        this.morada = morada;
        this.cidade = cidade;
        this.tipo = tipo;
    }

    public String getNome() { return nome; }
    public String getNif() { return nif; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getMorada() { return morada; }
    public String getCidade() { return cidade; }
    public FornecedorTipo getTipo() { return tipo; }
    public List<FornecedorCertificacao> getCertificacoes() { return certificacoes; }

    public void setNome(String nome) { this.nome = nome; }
    public void setNif(String nif) { this.nif = nif; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setMorada(String morada) { this.morada = morada; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setTipo(FornecedorTipo tipo) { this.tipo = tipo; }
    public void setCertificacoes(List<FornecedorCertificacao> certificacoes) { this.certificacoes = certificacoes; }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + getId() +
                ", nome='" + nome + '\'' +
                ", nif='" + nif + '\'' +
                '}';
    }
}