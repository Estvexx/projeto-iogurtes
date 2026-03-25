package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomendaOrdem;
import jakarta.persistence.*;



@Entity
@Table(name = "encomenda_ordens",
        uniqueConstraints = @UniqueConstraint(columnNames = {"ordem_id", "encomenda_pallet_id"}))
public class EncomendaOrdem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "ordem_id", nullable = false)
    private OrdemProducao ordem;

    @ManyToOne
    @JoinColumn(name = "encomenda_pallet_id", nullable = false)
    private EncomendaPallet encomendaPallet;

    @Column(name = "quantidade_pallets", nullable = false)
    private Integer quantidadePallets;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEncomendaOrdem estado = EstadoEncomendaOrdem.pendente;

    public EncomendaOrdem() {}

    public EncomendaOrdem(OrdemProducao ordem, EncomendaPallet encomendaPallet,
                          Integer quantidadePallets) {
        this.ordem = ordem;
        this.encomendaPallet = encomendaPallet;
        this.quantidadePallets = quantidadePallets;
        this.estado = EstadoEncomendaOrdem.pendente;
    }

    public OrdemProducao getOrdem() { return ordem; }
    public EncomendaPallet getEncomendaPallet() { return encomendaPallet; }
    public Integer getQuantidadePallets() { return quantidadePallets; }
    public EstadoEncomendaOrdem getEstado() { return estado; }

    public void setOrdem(OrdemProducao ordem) { this.ordem = ordem; }
    public void setEncomendaPallet(EncomendaPallet encomendaPallet) { this.encomendaPallet = encomendaPallet; }
    public void setQuantidadePallets(Integer quantidadePallets) { this.quantidadePallets = quantidadePallets; }
    public void setEstado(EstadoEncomendaOrdem estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "EncomendaOrdem{" +
                "id=" + getId() +
                ", estado=" + estado +
                ", quantidadePallets=" + quantidadePallets +
                '}';
    }
}