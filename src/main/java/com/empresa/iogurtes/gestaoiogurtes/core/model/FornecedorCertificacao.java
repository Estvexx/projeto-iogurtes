package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoCertificacao;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fornecedor_certificacoes")
public class FornecedorCertificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoCertificacao tipo;

    @Column(name = "descricao", length = 120)
    private String descricao;

    @Column(name = "validade")
    private LocalDate validade;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    public FornecedorCertificacao() {}

    public FornecedorCertificacao(Fornecedor fornecedor, TipoCertificacao tipo,
                                  String descricao, LocalDate validade) {
        this.fornecedor = fornecedor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.validade = validade;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public Fornecedor getFornecedor() { return fornecedor; }
    public TipoCertificacao getTipo() { return tipo; }
    public String getDescricao() { return descricao; }
    public LocalDate getValidade() { return validade; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(UUID id) { this.id = id; }
    public void setFornecedor(Fornecedor fornecedor) { this.fornecedor = fornecedor; }
    public void setTipo(TipoCertificacao tipo) { this.tipo = tipo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValidade(LocalDate validade) { this.validade = validade; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "FornecedorCertificacao{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", validade=" + validade +
                '}';
    }
}