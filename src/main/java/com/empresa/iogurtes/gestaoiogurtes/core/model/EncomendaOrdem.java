package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomendaOrdem;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "encomenda_ordens",
        uniqueConstraints = @UniqueConstraint(columnNames = {"ordem_id", "encomenda_pallet_id"}))
public class EncomendaOrdem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "updatedat")
    private LocalDateTime updatedAt;

    public EncomendaOrdem() {}

    public EncomendaOrdem(OrdemProducao ordem, EncomendaPallet encomendaPallet,
                          Integer quantidadePallets) {
        this.ordem = ordem;
        this.encomendaPallet = encomendaPallet;
        this.quantidadePallets = quantidadePallets;
        this.estado = EstadoEncomendaOrdem.pendente;
    }

    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public OrdemProducao getOrdem() { return ordem; }
    public EncomendaPallet getEncomendaPallet() { return encomendaPallet; }
    public Integer getQuantidadePallets() { return quantidadePallets; }
    public EstadoEncomendaOrdem getEstado() { return estado; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(UUID id) { this.id = id; }
    public void setOrdem(OrdemProducao ordem) { this.ordem = ordem; }
    public void setEncomendaPallet(EncomendaPallet encomendaPallet) { this.encomendaPallet = encomendaPallet; }
    public void setQuantidadePallets(Integer quantidadePallets) { this.quantidadePallets = quantidadePallets; }
    public void setEstado(EstadoEncomendaOrdem estado) { this.estado = estado; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "EncomendaOrdem{" +
                "id=" + id +
                ", estado=" + estado +
                ", quantidadePallets=" + quantidadePallets +
                '}';
    }
}