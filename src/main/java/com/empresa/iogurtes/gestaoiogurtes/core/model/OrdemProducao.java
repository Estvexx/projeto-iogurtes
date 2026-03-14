package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoOrdem;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ordens_producao")
public class OrdemProducao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoFinal produto;

    @Column(name = "quantidade_kg", precision = 12, scale = 3)
    private BigDecimal quantidadeKg;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoOrdem estado = EstadoOrdem.rascunho;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "aprovado_em")
    private LocalDateTime aprovadoEm;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "updatedat")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "ordem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsumoProducao> consumos;

    public OrdemProducao() {}

    public OrdemProducao(ProdutoFinal produto, BigDecimal quantidadeKg,
                         User user, String observacoes) {
        this.produto = produto;
        this.quantidadeKg = quantidadeKg;
        this.user = user;
        this.observacoes = observacoes;
        this.estado = EstadoOrdem.rascunho;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public ProdutoFinal getProduto() { return produto; }
    public BigDecimal getQuantidadeKg() { return quantidadeKg; }
    public EstadoOrdem getEstado() { return estado; }
    public LocalDateTime getDataInicio() { return dataInicio; }
    public LocalDateTime getDataFim() { return dataFim; }
    public User getUser() { return user; }
    public LocalDateTime getAprovadoEm() { return aprovadoEm; }
    public String getObservacoes() { return observacoes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<ConsumoProducao> getConsumos() { return consumos; }

    public void setId(Integer id) { this.id = id; }
    public void setProduto(ProdutoFinal produto) { this.produto = produto; }
    public void setQuantidadeKg(BigDecimal quantidadeKg) { this.quantidadeKg = quantidadeKg; }
    public void setEstado(EstadoOrdem estado) { this.estado = estado; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
    public void setUser(User user) { this.user = user; }
    public void setAprovadoEm(LocalDateTime aprovadoEm) { this.aprovadoEm = aprovadoEm; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setConsumos(List<ConsumoProducao> consumos) { this.consumos = consumos; }

    @Override
    public String toString() {
        return "OrdemProducao{" +
                "id=" + id +
                ", produto=" + produto.getNome() +
                ", estado=" + estado +
                ", quantidadeKg=" + quantidadeKg +
                '}';
    }
}