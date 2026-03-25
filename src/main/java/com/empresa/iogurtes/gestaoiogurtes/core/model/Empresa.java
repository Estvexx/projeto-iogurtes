package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresas")
public class Empresa extends BaseEntity {

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

    public String getNomeEmpresa() { return nomeEmpresa; }
        public String getNipc() { return nipc; }
        public String getTelefone() { return telefone; }
        public String getMorada() { return morada; }
        public String getCodigoPostal() { return codigoPostal; }
        public String getCidade() { return cidade; }

        public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }
        public void setNipc(String nipc) { this.nipc = nipc; }
        public void setTelefone(String telefone) { this.telefone = telefone; }
        public void setMorada(String morada) { this.morada = morada; }
        public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
        public void setCidade(String cidade) { this.cidade = cidade; }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + getId() +
                ", nomeEmpresa='" + nomeEmpresa + '\'' +
                ", nipc='" + nipc + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }
}
