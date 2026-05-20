package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomendaMP;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "encomendas_mp")
public class EncomendaMP extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moeda_id", nullable = false)
    private Moeda moeda;

    @Column(name = "taxa_conversao_snapshot", nullable = false, precision = 12, scale = 6)
    private BigDecimal taxaConversaoSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEncomendaMP estado = EstadoEncomendaMP.PENDENTE;

    @Column(name = "data_encomenda")
    private LocalDateTime dataEncomenda = LocalDateTime.now();

    @Column(name = "data_entrega_prevista")
    private LocalDate dataEntregaPrevista;

    @Column(name = "total_preco_sem_iva", precision = 12, scale = 2)
    private BigDecimal totalPrecoSemIva;

    @Column(name = "total_preco_eur_sem_iva", precision = 12, scale = 2)
    private BigDecimal totalPrecoEurSemIva;

    @Column(name = "total_preco_eur_com_iva", precision = 12, scale = 2)
    private BigDecimal totalPrecoEurComIva;

    @Column(name = "observacoes", length = 200)
    private String observacoes;

    public EncomendaMP() {}

    public EncomendaMP(User user, Fornecedor fornecedor, Moeda moeda,
                       BigDecimal taxaConversaoSnapshot, LocalDate dataEntregaPrevista,
                       BigDecimal totalPrecoSemIva, BigDecimal totalPrecoEurSemIva,
                       BigDecimal totalPrecoEurComIva, String observacoes) {
        this.user = user;
        this.fornecedor = fornecedor;
        this.moeda = moeda;
        this.taxaConversaoSnapshot = taxaConversaoSnapshot;
        this.dataEncomenda = LocalDateTime.now();
        this.dataEntregaPrevista = dataEntregaPrevista;
        this.totalPrecoSemIva = totalPrecoSemIva;
        this.totalPrecoEurSemIva = totalPrecoEurSemIva;
        this.totalPrecoEurComIva = totalPrecoEurComIva;
        this.observacoes = observacoes;
        this.estado = EstadoEncomendaMP.PENDENTE;
    }

    // Getters
    public User getUser() { return user; }
    public Fornecedor getFornecedor() { return fornecedor; }
    public Moeda getMoeda() { return moeda; }
    public BigDecimal getTaxaConversaoSnapshot() { return taxaConversaoSnapshot; }
    public EstadoEncomendaMP getEstado() { return estado; }
    public LocalDateTime getDataEncomenda() { return dataEncomenda; }
    public LocalDate getDataEntregaPrevista() { return dataEntregaPrevista; }
    public BigDecimal getTotalPrecoSemIva() { return totalPrecoSemIva; }
    public BigDecimal getTotalPrecoEurSemIva() { return totalPrecoEurSemIva; }
    public BigDecimal getTotalPrecoEurComIva() { return totalPrecoEurComIva; }
    public String getObservacoes() { return observacoes; }

    // Setters
    public void setEstado(EstadoEncomendaMP estado) { this.estado = estado; }
    public void setDataEntregaPrevista(LocalDate dataEntregaPrevista) { this.dataEntregaPrevista = dataEntregaPrevista; }
    public void setTotalPrecoSemIva(BigDecimal totalPrecoSemIva) { this.totalPrecoSemIva = totalPrecoSemIva; }
    public void setTotalPrecoEurSemIva(BigDecimal totalPrecoEurSemIva) { this.totalPrecoEurSemIva = totalPrecoEurSemIva; }
    public void setTotalPrecoEurComIva(BigDecimal totalPrecoEurComIva) { this.totalPrecoEurComIva = totalPrecoEurComIva; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
