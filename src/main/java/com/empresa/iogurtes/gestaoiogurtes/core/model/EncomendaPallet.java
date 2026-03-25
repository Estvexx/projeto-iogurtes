package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "encomenda_pallets",
        uniqueConstraints = @UniqueConstraint(columnNames = {"encomenda_id", "produto_id", "pallet_tipo_id"}))
public class EncomendaPallet extends BaseEntity {

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
    }
    public EncomendaPallet(UUID produtoId, UUID palletTipoId, Integer quantidadePallets, BigDecimal precoPorPallet) {
        this.produto = new ProdutoFinal();
        this.produto.setId(produtoId);
        this.palletTipo = new PalletTipo();
        this.palletTipo.setId(palletTipoId);
        this.quantidadePallets = quantidadePallets;
        this.precoPorPallet = precoPorPallet;
    }

    public Encomenda getEncomenda() { return encomenda; }
    public ProdutoFinal getProduto() { return produto; }
    public PalletTipo getPalletTipo() { return palletTipo; }
    public Integer getQuantidadePallets() { return quantidadePallets; }
    public BigDecimal getPrecoPorPallet() { return precoPorPallet; }
    public List<EncomendaOrdem> getOrdens() { return ordens; }

    public void setEncomenda(Encomenda encomenda) { this.encomenda = encomenda; }
    public void setProduto(ProdutoFinal produto) { this.produto = produto; }
    public void setPalletTipo(PalletTipo palletTipo) { this.palletTipo = palletTipo; }
    public void setQuantidadePallets(Integer quantidadePallets) { this.quantidadePallets = quantidadePallets; }
    public void setPrecoPorPallet(BigDecimal precoPorPallet) { this.precoPorPallet = precoPorPallet; }
    public void setOrdens(List<EncomendaOrdem> ordens) { this.ordens = ordens; }

    @Override
    public String toString() {
        return "EncomendaPallet{" +
                "id=" + getId() +
                ", produto=" + produto.getNome() +
                ", quantidadePallets=" + quantidadePallets +
                ", precoPorPallet=" + precoPorPallet +
                '}';
    }
}