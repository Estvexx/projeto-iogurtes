package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoPF;
import jakarta.persistence.*;

@Entity
@Table(name = "movimentos_stock_pf")
public class MovimentoStockPF extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoFinal produto;

    @ManyToOne
    @JoinColumn(name = "ordem_id", nullable = true)
    private OrdemProducao ordem;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMovimentoPF tipo;

    @Column(name = "quantidade_kg", nullable = false)
    private Integer quantidadeKg;

    @Column(name = "observacao", length = 200)
    private String observacao;

    public MovimentoStockPF() {}

    public MovimentoStockPF(ProdutoFinal produto, OrdemProducao ordem, TipoMovimentoPF tipo,
                            Integer quantidadeKg, String observacao) {
        this.produto = produto;
        this.ordem = ordem;
        this.tipo = tipo;
        this.quantidadeKg = quantidadeKg;
        this.observacao = observacao;
    }

    public ProdutoFinal getProduto() { return produto; }
    public OrdemProducao getOrdem() { return ordem; }
    public TipoMovimentoPF getTipo() { return tipo; }
    public Integer getQuantidadeKg() { return quantidadeKg; }
    public String getObservacao() { return observacao; }

    public void setProduto(ProdutoFinal produto) { this.produto = produto; }
    public void setOrdem(OrdemProducao ordem) { this.ordem = ordem; }
    public void setTipo(TipoMovimentoPF tipo) { this.tipo = tipo; }
    public void setQuantidadeKg(Integer quantidadeKg) { this.quantidadeKg = quantidadeKg; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    @Override
    public String toString() {
        return "MovimentoStockPF{" +
                "id=" + getId() +
                ", tipo=" + tipo +
                ", quantidadeKg=" + quantidadeKg +
                '}';
    }
}