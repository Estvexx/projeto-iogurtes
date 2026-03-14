package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "produtos_finais")
public class ProdutoFinal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo_sku", unique = true, nullable = false, length = 50)
    private String codigoSku;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "validade_dias")
    private Integer validadeDias;

    @Column(name = "preco_venda", precision = 10, scale = 2)
    private BigDecimal precoVenda;

    @Column(name = "preco_por_kg", precision = 10, scale = 2)
    private BigDecimal precoPorKg;

    @Column(name = "visivel_cliente")
    private Boolean visivelCliente = true;

    @Column(name = "stock_atual")
    private Integer stockAtual = 0;

    @Column(name = "quantidade_lote", nullable = false)
    private Integer quantidadeLote = 1;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "updatedat")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoMateria> materias;

    public ProdutoFinal() {}

    public ProdutoFinal(String codigoSku, String nome, String descricao,
                        Integer validadeDias, BigDecimal precoVenda,
                        BigDecimal precoPorKg, Integer quantidadeLote) {
        this.codigoSku = codigoSku;
        this.nome = nome;
        this.descricao = descricao;
        this.validadeDias = validadeDias;
        this.precoVenda = precoVenda;
        this.precoPorKg = precoPorKg;
        this.quantidadeLote = quantidadeLote;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public String getCodigoSku() { return codigoSku; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public Integer getValidadeDias() { return validadeDias; }
    public BigDecimal getPrecoVenda() { return precoVenda; }
    public BigDecimal getPrecoPorKg() { return precoPorKg; }
    public Boolean getVisivelCliente() { return visivelCliente; }
    public Integer getStockAtual() { return stockAtual; }
    public Integer getQuantidadeLote() { return quantidadeLote; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<ProdutoMateria> getMaterias() { return materias; }

    public void setId(Integer id) { this.id = id; }
    public void setCodigoSku(String codigoSku) { this.codigoSku = codigoSku; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValidadeDias(Integer validadeDias) { this.validadeDias = validadeDias; }
    public void setPrecoVenda(BigDecimal precoVenda) { this.precoVenda = precoVenda; }
    public void setPrecoPorKg(BigDecimal precoPorKg) { this.precoPorKg = precoPorKg; }
    public void setVisivelCliente(Boolean visivelCliente) { this.visivelCliente = visivelCliente; }
    public void setStockAtual(Integer stockAtual) { this.stockAtual = stockAtual; }
    public void setQuantidadeLote(Integer quantidadeLote) { this.quantidadeLote = quantidadeLote; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setMaterias(List<ProdutoMateria> materias) { this.materias = materias; }

    @Override
    public String toString() {
        return "ProdutoFinal{" +
                "id=" + id +
                ", codigoSku='" + codigoSku + '\'' +
                ", nome='" + nome + '\'' +
                ", stockAtual=" + stockAtual +
                '}';
    }
}