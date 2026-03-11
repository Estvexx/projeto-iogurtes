package com.iogurtes.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produto_materias",
        uniqueConstraints = @UniqueConstraint(columnNames = {"produto_id", "materia_id"}))
public class ProdutoMateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoFinal produto;

    @ManyToOne
    @JoinColumn(name = "materia_id", nullable = false)
    private MateriaPrima materia;

    @Column(name = "quantidade_por_unidade_produto", nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidadePorUnidadeProduto;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    public ProdutoMateria() {}

    public ProdutoMateria(ProdutoFinal produto, MateriaPrima materia,
                          BigDecimal quantidadePorUnidadeProduto) {
        this.produto = produto;
        this.materia = materia;
        this.quantidadePorUnidadeProduto = quantidadePorUnidadeProduto;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public ProdutoFinal getProduto() { return produto; }
    public MateriaPrima getMateria() { return materia; }
    public BigDecimal getQuantidadePorUnidadeProduto() { return quantidadePorUnidadeProduto; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Integer id) { this.id = id; }
    public void setProduto(ProdutoFinal produto) { this.produto = produto; }
    public void setMateria(MateriaPrima materia) { this.materia = materia; }
    public void setQuantidadePorUnidadeProduto(BigDecimal quantidade) { this.quantidadePorUnidadeProduto = quantidade; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "ProdutoMateria{" +
                "id=" + id +
                ", produto=" + produto.getNome() +
                ", materia=" + materia.getNome() +
                ", quantidade=" + quantidadePorUnidadeProduto +
                '}';
    }
}