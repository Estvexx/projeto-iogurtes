package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produto_materias",
        uniqueConstraints = @UniqueConstraint(columnNames = {"produto_id", "materia_id"}))
public class ProdutoMateria extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoFinal produto;

    @ManyToOne
    @JoinColumn(name = "materia_id", nullable = false)
    private MateriaPrima materia;

    @Column(name = "quantidade_por_unidade_produto", nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidadePorUnidadeProduto;

    public ProdutoMateria() {}

    public ProdutoMateria(ProdutoFinal produto, MateriaPrima materia,
                          BigDecimal quantidadePorUnidadeProduto) {
        this.produto = produto;
        this.materia = materia;
        this.quantidadePorUnidadeProduto = quantidadePorUnidadeProduto;
    }

    public ProdutoMateria(MateriaPrima materia, BigDecimal quantidadePorUnidadeProduto) {
        this.materia = materia;
        this.quantidadePorUnidadeProduto = quantidadePorUnidadeProduto;
    }

    public ProdutoFinal getProduto() { return produto; }
    public MateriaPrima getMateria() { return materia; }
    public BigDecimal getQuantidadePorUnidadeProduto() { return quantidadePorUnidadeProduto; }

    public void setProduto(ProdutoFinal produto) { this.produto = produto; }
    public void setMateria(MateriaPrima materia) { this.materia = materia; }
    public void setQuantidadePorUnidadeProduto(BigDecimal quantidade) { this.quantidadePorUnidadeProduto = quantidade; }

    @Override
    public String toString() {
        return "ProdutoMateria{" +
                "id=" + getId() +
                ", produto=" + produto.getNome() +
                ", materia=" + materia.getNome() +
                ", quantidade=" + quantidadePorUnidadeProduto +
                '}';
    }
}