package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoLote;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lotes_producao")
public class LoteProducao extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_id", nullable = false)
    private OrdemProducao ordem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoFinal produto;

    @Column(name = "numero_lote", nullable = false, length = 50)
    private String numeroLote;

    @Column(name = "quantidade_kg", nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidadeKg;

    @Column(name = "stock_atual_kg", nullable = false, precision = 12, scale = 3)
    private BigDecimal stockAtualKg;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoLote estado = EstadoLote.DISPONIVEL;

    @Column(name = "data_producao", nullable = false)
    private LocalDate dataProducao;

    @Column(name = "data_validade", nullable = false)
    private LocalDate dataValidade;

    public LoteProducao() {}

    public LoteProducao(OrdemProducao ordem, ProdutoFinal produto, String numeroLote,
                        BigDecimal quantidadeKg, LocalDate dataProducao, LocalDate dataValidade) {
        this.ordem = ordem;
        this.produto = produto;
        this.numeroLote = numeroLote;
        this.quantidadeKg = quantidadeKg;
        this.stockAtualKg = quantidadeKg;
        this.estado = EstadoLote.DISPONIVEL;
        this.dataProducao = dataProducao;
        this.dataValidade = dataValidade;
    }

    // Getters
    public OrdemProducao getOrdem() { return ordem; }
    public ProdutoFinal getProduto() { return produto; }
    public String getNumeroLote() { return numeroLote; }
    public BigDecimal getQuantidadeKg() { return quantidadeKg; }
    public BigDecimal getStockAtualKg() { return stockAtualKg; }
    public EstadoLote getEstado() { return estado; }
    public LocalDate getDataProducao() { return dataProducao; }
    public LocalDate getDataValidade() { return dataValidade; }

    // Setters
    public void setStockAtualKg(BigDecimal stockAtualKg) { this.stockAtualKg = stockAtualKg; }
    public void setEstado(EstadoLote estado) { this.estado = estado; }
}