package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoOrdem;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ordens_producao")
public class OrdemProducao extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoOrdem estado = EstadoOrdem.EM_PRODUCAO;

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

    @OneToMany(mappedBy = "ordem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdemProducaoProduto> produtos;

    @OneToMany(mappedBy = "ordem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsumoProducao> consumos;

    public OrdemProducao() {}

    public OrdemProducao(User user, LocalDateTime dataInicio, LocalDateTime dataFim,
                         String observacoes) {
        this.user = user;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.observacoes = observacoes;
        this.aprovadoEm = null;
    }

    public EstadoOrdem getEstado() { return estado; }
    public LocalDateTime getDataInicio() { return dataInicio; }
    public LocalDateTime getDataFim() { return dataFim; }
    public User getUser() { return user; }
    public LocalDateTime getAprovadoEm() { return aprovadoEm; }
    public String getObservacoes() { return observacoes; }
    public List<OrdemProducaoProduto> getProdutos() { return produtos; }
    public List<ConsumoProducao> getConsumos() { return consumos; }

    public void setEstado(EstadoOrdem estado) { this.estado = estado; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
    public void setUser(User user) { this.user = user; }
    public void setAprovadoEm(LocalDateTime aprovadoEm) { this.aprovadoEm = aprovadoEm; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public void setProdutos(List<OrdemProducaoProduto> produtos) { this.produtos = produtos; }
    public void setConsumos(List<ConsumoProducao> consumos) { this.consumos = consumos; }

    @Override
    public String toString() {
        return "OrdemProducao{" +
                "id=" + getId() +
                ", estado=" + estado +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                '}';
    }
}