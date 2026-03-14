package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "cargo", length = 80)
    private String cargo;

    @Enumerated(EnumType.STRING)
    @Column(name = "turno")
    private TurnoTipo turno;

    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "updatedat")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> roles;

    public User() {}

    public User(Empresa empresa, String nome, String email, String passwordHash,
                String cargo, TurnoTipo turno, LocalDate dataAdmissao) {
        this.empresa = empresa;
        this.nome = nome;
        this.email = email;
        this.passwordHash = passwordHash;
        this.cargo = cargo;
        this.turno = turno;
        this.dataAdmissao = dataAdmissao;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public Empresa getEmpresa() { return empresa; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getCargo() { return cargo; }
    public TurnoTipo getTurno() { return turno; }
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<UserRole> getRoles() { return roles; }

    public void setId(Integer id) { this.id = id; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public void setTurno(TurnoTipo turno) { this.turno = turno; }
    public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setRoles(List<UserRole> roles) { this.roles = roles; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cargo='" + cargo + '\'' +
                ", turno=" + turno +
                '}';
    }
}