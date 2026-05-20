package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoOrdem;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ordens_producao")
public class OrdemProducao extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoOrdem estado = EstadoOrdem.EM_PRODUCAO;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @Column(name = "aprovado_em")
    private LocalDateTime aprovadoEm;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    public OrdemProducao() {}

    public OrdemProducao(User user, String observacoes) {
        this.user = user;
        this.estado = EstadoOrdem.EM_PRODUCAO;
        this.dataInicio = LocalDateTime.now();
        this.observacoes = observacoes;
    }

    // Getters
    public User getUser() { return user; }
    public EstadoOrdem getEstado() { return estado; }
    public LocalDateTime getDataInicio() { return dataInicio; }
    public LocalDateTime getDataFim() { return dataFim; }
    public LocalDateTime getAprovadoEm() { return aprovadoEm; }
    public String getObservacoes() { return observacoes; }

    // Setters
    public void setEstado(EstadoOrdem estado) { this.estado = estado; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
    public void setAprovadoEm(LocalDateTime aprovadoEm) { this.aprovadoEm = aprovadoEm; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}