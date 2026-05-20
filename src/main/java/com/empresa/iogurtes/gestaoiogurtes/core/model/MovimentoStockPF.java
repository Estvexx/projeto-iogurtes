package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoPF;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "movimentos_stock_pf")
public class MovimentoStockPF extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lote_id", nullable = false)
    private LoteProducao lote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMovimentoPF tipo;

    @Column(name = "quantidade_kg", nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidadeKg;

    @Column(name = "observacao", length = 200)
    private String observacao;

    public MovimentoStockPF() {}

    public MovimentoStockPF(LoteProducao lote, User user, TipoMovimentoPF tipo,
                            BigDecimal quantidadeKg, String observacao) {
        this.lote = lote;
        this.user = user;
        this.tipo = tipo;
        this.quantidadeKg = quantidadeKg;
        this.observacao = observacao;
    }

    // Getters
    public LoteProducao getLote() { return lote; }
    public User getUser() { return user; }
    public TipoMovimentoPF getTipo() { return tipo; }
    public BigDecimal getQuantidadeKg() { return quantidadeKg; }
    public String getObservacao() { return observacao; }
}