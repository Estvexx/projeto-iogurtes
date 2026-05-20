package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "encomenda_pallets",
        uniqueConstraints = @UniqueConstraint(columnNames = {"encomenda_id", "produto_id", "pallet_tipo_id"}))
public class EncomendaPallet extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encomenda_id", nullable = false)
    private Encomenda encomenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoFinal produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pallet_tipo_id", nullable = false)
    private PalletTipo palletTipo;

    @Column(name = "quantidade_pallets", nullable = false)
    private Integer quantidadePallets;

    @Column(name = "preco_por_pallet_eur", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoPorPalletEur;

    @Column(name = "taxa_iva", nullable = false, precision = 5, scale = 2)
    private BigDecimal taxaIva;

    public EncomendaPallet() {}

    public EncomendaPallet(Encomenda encomenda, ProdutoFinal produto, PalletTipo palletTipo,
                           Integer quantidadePallets, BigDecimal precoPorPalletEur, BigDecimal taxaIva) {
        this.encomenda = encomenda;
        this.produto = produto;
        this.palletTipo = palletTipo;
        this.quantidadePallets = quantidadePallets;
        this.precoPorPalletEur = precoPorPalletEur;
        this.taxaIva = taxaIva;
    }

    // Getters
    public Encomenda getEncomenda() { return encomenda; }
    public ProdutoFinal getProduto() { return produto; }
    public PalletTipo getPalletTipo() { return palletTipo; }
    public Integer getQuantidadePallets() { return quantidadePallets; }
    public BigDecimal getPrecoPorPalletEur() { return precoPorPalletEur; }
    public BigDecimal getTaxaIva() { return taxaIva; }

    // Setters
    public void setQuantidadePallets(Integer quantidadePallets) { this.quantidadePallets = quantidadePallets; }
    public void setPrecoPorPalletEur(BigDecimal precoPorPalletEur) { this.precoPorPalletEur = precoPorPalletEur; }
    public void setTaxaIva(BigDecimal taxaIva) { this.taxaIva = taxaIva; }
}