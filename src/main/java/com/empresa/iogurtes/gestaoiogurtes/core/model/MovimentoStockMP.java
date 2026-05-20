package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoMP;
import jakarta.persistence.*;

@Entity
@Table(name = "movimentos_stock_mp")
public class MovimentoStockMP extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMovimentoMP tipo;

    @Column(name = "observacao", length = 200)
    private String observacao;

    public MovimentoStockMP() {}

    public MovimentoStockMP(User user, TipoMovimentoMP tipo, String observacao) {
        this.user = user;
        this.tipo = tipo;
        this.observacao = observacao;
    }

    // Getters
    public User getUser() { return user; }
    public TipoMovimentoMP getTipo() { return tipo; }
    public String getObservacao() { return observacao; }

    // Setters
    public void setObservacao(String observacao) { this.observacao = observacao; }
}