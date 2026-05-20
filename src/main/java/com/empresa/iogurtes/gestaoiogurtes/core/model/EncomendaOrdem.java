package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomendaOrdem;
import jakarta.persistence.*;

@Entity
@Table(name = "encomenda_ordens",
        uniqueConstraints = @UniqueConstraint(columnNames = {"ordem_id", "encomenda_pallet_id"}))
public class EncomendaOrdem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_id", nullable = false)
    private OrdemProducao ordem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encomenda_pallet_id", nullable = false)
    private EncomendaPallet encomendaPallet;

    @Column(name = "quantidade_pallets", nullable = false)
    private Integer quantidadePallets;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEncomendaOrdem estado = EstadoEncomendaOrdem.PENDENTE;

    public EncomendaOrdem() {}

    public EncomendaOrdem(OrdemProducao ordem, EncomendaPallet encomendaPallet, Integer quantidadePallets) {
        this.ordem = ordem;
        this.encomendaPallet = encomendaPallet;
        this.quantidadePallets = quantidadePallets;
        this.estado = EstadoEncomendaOrdem.PENDENTE;
    }

    // Getters
    public OrdemProducao getOrdem() { return ordem; }
    public EncomendaPallet getEncomendaPallet() { return encomendaPallet; }
    public Integer getQuantidadePallets() { return quantidadePallets; }
    public EstadoEncomendaOrdem getEstado() { return estado; }

    // Setters
    public void setEstado(EstadoEncomendaOrdem estado) { this.estado = estado; }
}