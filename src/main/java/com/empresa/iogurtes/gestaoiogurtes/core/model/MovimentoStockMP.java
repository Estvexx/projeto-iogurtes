package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoMP;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "movimentos_stock_mp")
public class MovimentoStockMP extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "materia_id", nullable = false)
    private MateriaPrima materia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMovimentoMP tipo;

    @Column(name = "quantidade", nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidade;

    @Column(name = "observacao", length = 200)
    private String observacao;

    public MovimentoStockMP() {}

    public MovimentoStockMP(User user, MateriaPrima materia, TipoMovimentoMP tipo,
                            BigDecimal quantidade, String observacao) {
        this.user = user;
        this.materia = materia;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.observacao = observacao;
    }

    public User getUser() { return user; }
    public MateriaPrima getMateria() { return materia; }
    public TipoMovimentoMP getTipo() { return tipo; }
    public BigDecimal getQuantidade() { return quantidade; }
    public String getObservacao() { return observacao; }

    public void setUser(User user) { this.user = user; }
    public void setMateria(MateriaPrima materia) { this.materia = materia; }
    public void setTipo(TipoMovimentoMP tipo) { this.tipo = tipo; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    @Override
    public String toString() {
        return "MovimentoStockMP{" +
                "id=" + getId() +
                ", tipo=" + tipo +
                ", quantidade=" + quantidade +
                '}';
    }
}