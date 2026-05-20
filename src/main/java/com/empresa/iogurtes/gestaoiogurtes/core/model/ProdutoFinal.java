package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoFisico;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos_finais")
public class ProdutoFinal extends BaseEntity {

    @Column(name = "codigo_sku", nullable = false, unique = true, length = 50)
    private String codigoSku;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "abreviacao_sabor", nullable = false, length = 3)
    private String abreviacaoSabor;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_fisico", nullable = false)
    private EstadoFisico estadoFisico;

    @Column(name = "validade_dias")
    private Integer validadeDias;

    @Column(name = "preco_venda", precision = 10, scale = 2)
    private BigDecimal precoVenda;

    @Column(name = "preco_por_kg", precision = 10, scale = 2)
    private BigDecimal precoPorKg;

    @Column(name = "taxa_iva", nullable = false, precision = 5, scale = 2)
    private BigDecimal taxaIva = new BigDecimal("6.00");

    @Column(name = "visivel_cliente")
    private boolean visivelCliente = true;

    @Column(name = "quantidade_lote", nullable = false)
    private Integer quantidadeLote = 1;

    public ProdutoFinal() {}

    public ProdutoFinal(String codigoSku, String nome, String descricao, String abreviacaoSabor,
                        EstadoFisico estadoFisico, Integer validadeDias, BigDecimal precoVenda,
                        BigDecimal precoPorKg, BigDecimal taxaIva, boolean visivelCliente,
                        Integer quantidadeLote) {
        this.codigoSku = codigoSku;
        this.nome = nome;
        this.descricao = descricao;
        this.abreviacaoSabor = abreviacaoSabor.toUpperCase();
        this.estadoFisico = estadoFisico;
        this.validadeDias = validadeDias;
        this.precoVenda = precoVenda;
        this.precoPorKg = precoPorKg;
        this.taxaIva = taxaIva;
        this.visivelCliente = visivelCliente;
        this.quantidadeLote = quantidadeLote;
    }

    // Getters
    public String getCodigoSku() { return codigoSku; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getAbreviacaoSabor() { return abreviacaoSabor; }
    public EstadoFisico getEstadoFisico() { return estadoFisico; }
    public Integer getValidadeDias() { return validadeDias; }
    public BigDecimal getPrecoVenda() { return precoVenda; }
    public BigDecimal getPrecoPorKg() { return precoPorKg; }
    public BigDecimal getTaxaIva() { return taxaIva; }
    public boolean isVisivelCliente() { return visivelCliente; }
    public Integer getQuantidadeLote() { return quantidadeLote; }

    // Setters
    public void setCodigoSku(String codigoSku) { this.codigoSku = codigoSku; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setAbreviacaoSabor(String abreviacaoSabor) { this.abreviacaoSabor = abreviacaoSabor.toUpperCase(); }
    public void setEstadoFisico(EstadoFisico estadoFisico) { this.estadoFisico = estadoFisico; }
    public void setValidadeDias(Integer validadeDias) { this.validadeDias = validadeDias; }
    public void setPrecoVenda(BigDecimal precoVenda) { this.precoVenda = precoVenda; }
    public void setPrecoPorKg(BigDecimal precoPorKg) { this.precoPorKg = precoPorKg; }
    public void setTaxaIva(BigDecimal taxaIva) { this.taxaIva = taxaIva; }
    public void setVisivelCliente(boolean visivelCliente) { this.visivelCliente = visivelCliente; }
    public void setQuantidadeLote(Integer quantidadeLote) { this.quantidadeLote = quantidadeLote; }
}