package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.repository.EmpresaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.EmpresaValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Empresa;

import java.util.UUID;
import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final EmpresaValidator empresaValidator;

    public EmpresaService(EmpresaRepository empresaRepository,
                          UserRepository userRepository,
                          UserService userService,
                          EmpresaValidator empresaValidator) {
        this.empresaRepository = empresaRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.empresaValidator = empresaValidator;
    }

    @Transactional
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
        return empresaRepository.findAllByIsActiveTrue();
    }

    public List<Empresa> getAllIncludingInactive() {
        return empresaRepository.findAll();
    }

    @Transactional
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

    @Transactional
    public void delete(UUID id) {
        // Coloquei o get para simplesmente nao poder dar delete a algo que não existe
        Empresa empresa = getById(id);

        userRepository.findByEmpresaId(id)
                .forEach(user -> userService.delete(user.getId()));

        empresa.softDelete();
        empresaRepository.save(empresa);
    }
}
