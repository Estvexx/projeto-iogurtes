package com.iogurtes.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "encomenda_pallets",
        uniqueConstraints = @UniqueConstraint(columnNames = {"encomenda_id", "produto_id", "pallet_tipo_id"}))
public class EncomendaPallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "encomenda_id", nullable = false)
    private Encomenda encomenda;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoFinal produto;

    @ManyToOne
    @JoinColumn(name = "pallet_tipo_id", nullable = false)
    private PalletTipo palletTipo;

    @Column(name = "quantidade_pallets", nullable = false)
    private Integer quantidadePallets;

    @Column(name = "preco_por_pallet", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoPorPallet;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "updatedat")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "encomendaPallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EncomendaOrdem> ordens;

    public EncomendaPallet() {}

    public EncomendaPallet(Encomenda encomenda, ProdutoFinal produto,
                           PalletTipo palletTipo, Integer quantidadePallets,
                           BigDecimal precoPorPallet) {
        this.encomenda = encomenda;
        this.produto = produto;
        this.palletTipo = palletTipo;
        this.quantidadePallets = quantidadePallets;
        this.precoPorPallet = precoPorPallet;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public Encomenda getEncomenda() { return encomenda; }
    public ProdutoFinal getProduto() { return produto; }
    public PalletTipo getPalletTipo() { return palletTipo; }
    public Integer getQuantidadePallets() { return quantidadePallets; }
    public BigDecimal getPrecoPorPallet() { return precoPorPallet; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<EncomendaOrdem> getOrdens() { return ordens; }

    public void setId(Integer id) { this.id = id; }
    public void setEncomenda(Encomenda encomenda) { this.encomenda = encomenda; }
    public void setProduto(ProdutoFinal produto) { this.produto = produto; }
    public void setPalletTipo(PalletTipo palletTipo) { this.palletTipo = palletTipo; }
    public void setQuantidadePallets(Integer quantidadePallets) { this.quantidadePallets = quantidadePallets; }
    public void setPrecoPorPallet(BigDecimal precoPorPallet) { this.precoPorPallet = precoPorPallet; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setOrdens(List<EncomendaOrdem> ordens) { this.ordens = ordens; }

    @Override
    public String toString() {
        return "EncomendaPallet{" +
                "id=" + id +
                ", produto=" + produto.getNome() +
                ", quantidadePallets=" + quantidadePallets +
                ", precoPorPallet=" + precoPorPallet +
                '}';
    }
}