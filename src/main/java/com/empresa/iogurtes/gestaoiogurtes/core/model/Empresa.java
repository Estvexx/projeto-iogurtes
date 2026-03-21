    package com.empresa.iogurtes.gestaoiogurtes.core.model;

    import jakarta.persistence.*;

    import java.time.LocalDateTime;
    import java.util.UUID;


    @Entity
    @Table(name = "empresas")
    public class Empresa {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(name = "nome_empresa", nullable = false, length = 150)
        private String nomeEmpresa;

        @Column(name = "nipc", unique = true, nullable = false, length = 20)
        private String nipc;

        @Column(name = "telefone", length = 20)
        private String telefone;

        @Column(name = "morada", length = 200)
        private String morada;

        @Column(name = "codigo_postal", length = 20)
        private String codigoPostal;

        @Column(name = "cidade", length = 100)
        private String cidade;

        @Column(name = "createdat")
        private LocalDateTime createdAt;

        @Column(name = "updatedat")
        private LocalDateTime updatedAt;

        public Empresa() {}

        public Empresa(String nomeEmpresa, String nipc, String telefone,
                       String morada, String codigoPostal, String cidade) {
            this.nomeEmpresa = nomeEmpresa;
            this.nipc = nipc;
            this.telefone = telefone;
            this.morada = morada;
            this.codigoPostal = codigoPostal;
            this.cidade = cidade;
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
        public String getNomeEmpresa() { return nomeEmpresa; }
        public String getNipc() { return nipc; }
        public String getTelefone() { return telefone; }
        public String getMorada() { return morada; }
        public String getCodigoPostal() { return codigoPostal; }
        public String getCidade() { return cidade; }

        public void setId(UUID id) { this.id = id; }
        public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }
        public void setNipc(String nipc) { this.nipc = nipc; }
        public void setTelefone(String telefone) { this.telefone = telefone; }
        public void setMorada(String morada) { this.morada = morada; }
        public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
        public void setCidade(String cidade) { this.cidade = cidade; }

        @Override
        public String toString() {
            return "Empresa{" +
                    "id=" + id +
                    ", nomeEmpresa='" + nomeEmpresa + '\'' +
                    ", nipc='" + nipc + '\'' +
                    ", cidade='" + cidade + '\'' +
                    '}';
        }
    }