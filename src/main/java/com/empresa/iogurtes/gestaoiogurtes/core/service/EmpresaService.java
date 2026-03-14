package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.repository.EmpresaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.EmpresaValidator;
import org.springframework.stereotype.Service;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Empresa;

import java.util.UUID;
import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EmpresaValidator empresaValidator;

    public EmpresaService(EmpresaRepository empresaRepository, EmpresaValidator empresaValidator) {
        this.empresaRepository = empresaRepository;
        this.empresaValidator = empresaValidator;
    }

    public Empresa createEmpresa(String nomeEmpresa, String nipc, String telefone,
                                String morada, String codigoPostal, String cidade) {

        empresaValidator.validateCreateEmpresa(nomeEmpresa, nipc,telefone, morada, codigoPostal, cidade);

        Empresa empresa = new Empresa(nomeEmpresa, nipc, telefone, morada, codigoPostal, cidade);
        return empresaRepository.save(empresa);
    }

    public Empresa getById(UUID id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada!"));
    }

    public List<Empresa> getAll() {
        return empresaRepository.findAll();
    }

    public Empresa update(UUID id, String nomeEmpresa, String nipc, String telefone,
                          String morada, String codigoPostal, String cidade) {

        empresaValidator.validateUpdateEmpresa(id, nomeEmpresa, nipc,telefone, morada, codigoPostal, cidade);

        Empresa empresa = getById(id);
        empresa.setNomeEmpresa(nomeEmpresa);
        empresa.setNipc(nipc);
        empresa.setTelefone(telefone);
        empresa.setMorada(morada);
        empresa.setCodigoPostal(codigoPostal);
        empresa.setCidade(cidade);

        return empresaRepository.save(empresa);
    }

    public void delete(UUID id) {
        // Coloquei o get para simplesmente nao poder dar delete a algo que não existe
        Empresa empresa = getById(id);
        empresaRepository.delete(empresa);
    }
}
