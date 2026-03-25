package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "turno")
    private TurnoTipo turno;

    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> roles = new ArrayList<>();

    public User() {}

    public User(Empresa empresa, String nome, String email, String passwordHash,
                 TurnoTipo turno, LocalDate dataAdmissao) {
        this.empresa = empresa;
        this.nome = nome;
        this.email = email;
        this.passwordHash = passwordHash;
        this.turno = turno;
        this.dataAdmissao = dataAdmissao;
    }

    public Empresa getEmpresa() { return empresa; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public TurnoTipo getTurno() { return turno; }
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public List<UserRole> getRoles() { return roles; }

    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setTurno(TurnoTipo turno) { this.turno = turno; }
    public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }
    public void setRoles(List<UserRole> roles) { this.roles = roles; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", turno=" + turno +
                '}';
    }
}