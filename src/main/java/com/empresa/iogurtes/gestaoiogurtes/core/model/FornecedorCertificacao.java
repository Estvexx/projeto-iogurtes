package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoCertificacao;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "fornecedor_certificacoes")
public class FornecedorCertificacao extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoCertificacao tipo;

    @Column(name = "descricao", length = 120)
    private String descricao;

    @Column(name = "data_validade")
    private LocalDate data_validade;

    public FornecedorCertificacao() {}

    public FornecedorCertificacao(TipoCertificacao tipo, String descricao, LocalDate data_validade) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.data_validade = data_validade;
    }
    public FornecedorCertificacao(Fornecedor fornecedor, TipoCertificacao tipo,
                                  String descricao, LocalDate data_validade) {
        this.fornecedor = fornecedor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.data_validade = data_validade;
    }

    public Fornecedor getFornecedor() { return fornecedor; }
    public TipoCertificacao getTipo() { return tipo; }
    public String getDescricao() { return descricao; }
    public LocalDate getValidade() { return data_validade; }

    public void setFornecedor(Fornecedor fornecedor) { this.fornecedor = fornecedor; }
    public void setTipo(TipoCertificacao tipo) { this.tipo = tipo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValidade(LocalDate data_validade) { this.data_validade = data_validade; }

    @Override
    public String toString() {
        return "FornecedorCertificacao{" +
                "id=" + getId() +
                ", tipo=" + tipo +
                ", data_validade=" + data_validade +
                '}';
    }
}