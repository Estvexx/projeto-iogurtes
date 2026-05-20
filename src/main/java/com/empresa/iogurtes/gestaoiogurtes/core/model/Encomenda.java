package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomenda;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "encomendas")
public class Encomenda extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moeda_id", nullable = false)
    private Moeda moeda;

    @Column(name = "taxa_conversao_snapshot", nullable = false, precision = 12, scale = 6)
    private BigDecimal taxaConversaoSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEncomenda estado = EstadoEncomenda.PENDENTE;

    @Column(name = "data_encomenda")
    private LocalDateTime dataEncomenda = LocalDateTime.now();

    @Column(name = "total_preco", precision = 12, scale = 2)
    private BigDecimal totalPreco;

    @Column(name = "total_preco_eur", precision = 12, scale = 2)
    private BigDecimal totalPrecoEur;

    public Encomenda() {}

    public Encomenda(User user, Moeda moeda, BigDecimal taxaConversaoSnapshot,
                     BigDecimal totalPreco, BigDecimal totalPrecoEur) {
        this.user = user;
        this.moeda = moeda;
        this.taxaConversaoSnapshot = taxaConversaoSnapshot;
        this.dataEncomenda = LocalDateTime.now();
        this.estado = EstadoEncomenda.PENDENTE;
        this.totalPreco = totalPreco;
        this.totalPrecoEur = totalPrecoEur;
    }

    // Getters
    public User getUser() { return user; }
    public Moeda getMoeda() { return moeda; }
    public BigDecimal getTaxaConversaoSnapshot() { return taxaConversaoSnapshot; }
    public EstadoEncomenda getEstado() { return estado; }
    public LocalDateTime getDataEncomenda() { return dataEncomenda; }
    public BigDecimal getTotalPreco() { return totalPreco; }
    public BigDecimal getTotalPrecoEur() { return totalPrecoEur; }

    // Setters
    public void setEstado(EstadoEncomenda estado) { this.estado = estado; }
    public void setTotalPreco(BigDecimal totalPreco) { this.totalPreco = totalPreco; }
    public void setTotalPrecoEur(BigDecimal totalPrecoEur) { this.totalPrecoEur = totalPrecoEur; }
}