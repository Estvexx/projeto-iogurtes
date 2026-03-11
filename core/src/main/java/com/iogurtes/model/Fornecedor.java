package com.iogurtes.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "updatedat")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FornecedorCertificacao> certificacoes;

    // ─── Construtores ─────────────────────────────────────────────
    public Fornecedor() {}

    public Fornecedor(String nome, String nif, String email,
                      String telefone, String morada) {
        this.nome = nome;
        this.nif = nif;
        this.email = email;
        this.telefone = telefone;
        this.morada = morada;
        this.createdAt = LocalDateTime.now();
    }

    // ─── Getters ──────────────────────────────────────────────────
    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public String getNif() { return nif; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getMorada() { return morada; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<FornecedorCertificacao> getCertificacoes() { return certificacoes; }

    // ─── Setters ──────────────────────────────────────────────────
    public void setId(Integer id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setNif(String nif) { this.nif = nif; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setMorada(String morada) { this.morada = morada; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setCertificacoes(List<FornecedorCertificacao> certificacoes) { this.certificacoes = certificacoes; }

    // ─── toString ─────────────────────────────────────────────────
    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nif='" + nif + '\'' +
                '}';
    }
}