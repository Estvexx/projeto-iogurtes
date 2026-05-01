package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "fornecedor_certificacoes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"fornecedor_id", "certificacao_id"}))

public class FornecedorCertificacao extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @ManyToOne
    @JoinColumn(name = "certificacao_id", nullable = false)
    private Certificacao certificacao;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    public FornecedorCertificacao() {}

    public FornecedorCertificacao(Fornecedor fornecedor, Certificacao certificacao,
                                  LocalDate dataInicio, LocalDate dataFim) {
        this.fornecedor = fornecedor;
        this.certificacao = certificacao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Fornecedor getFornecedor() { return fornecedor; }
    public Certificacao getCertificacao() { return certificacao; }
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getDataFim() { return dataFim; }

    public void setFornecedor(Fornecedor fornecedor) { this.fornecedor = fornecedor; }
    public void setCertificacao(Certificacao certificacao) { this.certificacao = certificacao; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    @Override
    public String toString() {
        return "FornecedorCertificacao{" +
                "id=" + getId() +
                ", fornecedor=" + fornecedor.getNome() +
                ", certificacao=" + certificacao.getNome() +
                '}';
    }
}