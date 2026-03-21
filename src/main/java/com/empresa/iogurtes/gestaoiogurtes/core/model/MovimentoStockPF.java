package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TipoMovimentoPF;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "movimentos_stock_pf")
public class MovimentoStockPF {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    public MovimentoStockPF() {}

    public MovimentoStockPF(ProdutoFinal produto, OrdemProducao ordem, TipoMovimentoPF tipo,
                            Integer quantidadeKg, String observacao) {
        this.produto = produto;
        this.ordem = ordem;
        this.tipo = tipo;
        this.quantidadeKg = quantidadeKg;
        this.observacao = observacao;
    }

    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public ProdutoFinal getProduto() { return produto; }
    public OrdemProducao getOrdem() { return ordem; }
    public TipoMovimentoPF getTipo() { return tipo; }
    public Integer getQuantidadeKg() { return quantidadeKg; }
    public String getObservacao() { return observacao; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(UUID id) { this.id = id; }
    public void setProduto(ProdutoFinal produto) { this.produto = produto; }
    public void setOrdem(OrdemProducao ordem) { this.ordem = ordem; }
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