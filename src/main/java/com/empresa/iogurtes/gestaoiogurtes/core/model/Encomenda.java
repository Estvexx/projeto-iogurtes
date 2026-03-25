package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomenda;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "encomendas")
public class Encomenda extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoEncomenda estado = EstadoEncomenda.pendente;

    @Column(name = "data_encomenda")
    private LocalDateTime dataEncomenda;

    @Column(name = "total_preco", precision = 12, scale = 2)
    private BigDecimal totalPreco;

    @OneToMany(mappedBy = "encomenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EncomendaPallet> pallets;

    public Encomenda() {}

    public Encomenda(User user, BigDecimal totalPreco) {
        this.user = user;
        this.totalPreco = totalPreco;
        this.estado = EstadoEncomenda.pendente;
        this.dataEncomenda = LocalDateTime.now();
    }

    public User getUser() { return user; }
    public EstadoEncomenda getEstado() { return estado; }
    public LocalDateTime getDataEncomenda() { return dataEncomenda; }
    public BigDecimal getTotalPreco() { return totalPreco; }
    public List<EncomendaPallet> getPallets() { return pallets; }

    public void setUser(User user) { this.user = user; }
    public void setEstado(EstadoEncomenda estado) { this.estado = estado; }
    public void setDataEncomenda(LocalDateTime dataEncomenda) { this.dataEncomenda = dataEncomenda; }
    public void setTotalPreco(BigDecimal totalPreco) { this.totalPreco = totalPreco; }
    public void setPallets(List<EncomendaPallet> pallets) { this.pallets = pallets; }

    @Override
    public String toString() {
        return "Encomenda{" +
                "id=" + getId() +
                ", user=" + user.getNome() +
                ", estado=" + estado +
                ", totalPreco=" + totalPreco +
                '}';
    }
}