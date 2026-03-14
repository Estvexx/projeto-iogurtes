package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoPF;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentos_stock_pf")
public class MovimentoStockPF {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoFinal produto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMovimentoPF tipo;

    @Column(name = "quantidade_kg", nullable = false)
    private Integer quantidadeKg;

    @Column(name = "observacao", length = 200)
    private String observacao;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    public MovimentoStockPF() {}

    public MovimentoStockPF(User user, ProdutoFinal produto, TipoMovimentoPF tipo,
                            Integer quantidadeKg, String observacao) {
        this.user = user;
        this.produto = produto;
        this.tipo = tipo;
        this.quantidadeKg = quantidadeKg;
        this.observacao = observacao;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public User getUser() { return user; }
    public ProdutoFinal getProduto() { return produto; }
    public TipoMovimentoPF getTipo() { return tipo; }
    public Integer getQuantidadeKg() { return quantidadeKg; }
    public String getObservacao() { return observacao; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Integer id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setProduto(ProdutoFinal produto) { this.produto = produto; }
    public void setTipo(TipoMovimentoPF tipo) { this.tipo = tipo; }
    public void setQuantidadeKg(Integer quantidadeKg) { this.quantidadeKg = quantidadeKg; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "MovimentoStockPF{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", quantidadeKg=" + quantidadeKg +
                '}';
    }
}