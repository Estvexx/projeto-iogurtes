package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.domain.empresa.dto.*;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.empresa.EmpresaErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.empresa.EmpresaException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Empresa;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.EmpresaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.EmpresaValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final UserRepository userRepository;
    private final EmpresaValidator empresaValidator;

    public EmpresaService(EmpresaRepository empresaRepository,
                          UserRepository userRepository,
                          EmpresaValidator empresaValidator) {
        this.empresaRepository = empresaRepository;
        this.userRepository = userRepository;
        this.empresaValidator = empresaValidator;
    }


    @Transactional
    public EmpresaResponse createEmpresa(CreateEmpresaRequest request) {
        ValidatedEmpresa info = empresaValidator.validateCreateEmpresa(request);

        try {
            Empresa empresa = new Empresa(
                    info.nomeEmpresa(),
                    info.nipc(),
                    info.telefone(),
                    info.morada(),
                    info.codigoPostal(),
                    info.cidade()
            );
            return toResponse(empresaRepository.save(empresa));
        } catch (Exception e) {
            throw new EmpresaException(EmpresaErrorCode.EMPRESA_CREATE_FAILED);
        }
    }

    public EmpresaResponse findById(UUID id) {
        return empresaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EmpresaException(EmpresaErrorCode.EMPRESA_NOT_FOUND));
    }

    public List<EmpresaResponse> findAll() {
        return empresaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<EmpresaResponse> findAllActive() {
        return empresaRepository.findAllByIsActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<EmpresaResponse> findAllInactive() {
        return empresaRepository.findAllByIsActiveFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }


    @Transactional
    public EmpresaResponse updateEmpresa(UUID id, UpdateEmpresaRequest request) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new EmpresaException(EmpresaErrorCode.EMPRESA_NOT_FOUND));

        ValidatedUpdateEmpresa info = empresaValidator.validateUpdateEmpresa(id, request);

        try {
            empresa.setNomeEmpresa(info.nomeEmpresa());
            empresa.setTelefone(info.telefone());
            empresa.setMorada(info.morada());
            empresa.setCodigoPostal(info.codigoPostal());
            empresa.setCidade(info.cidade());
            return toResponse(empresaRepository.save(empresa));
        } catch (Exception e) {
            throw new EmpresaException(EmpresaErrorCode.EMPRESA_UPDATE_FAILED);
        }
    }

    @Transactional
    public void softDelete(UUID id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new EmpresaException(EmpresaErrorCode.EMPRESA_NOT_FOUND));

        if (userRepository.existsByEmpresa_IdAndIsActiveTrue(id)) {
            throw new EmpresaException(EmpresaErrorCode.EMPRESA_HAS_CLIENTES);
        }

        empresa.softDelete();
        empresaRepository.save(empresa);
    }

    private EmpresaResponse toResponse(Empresa empresa) {
        return new EmpresaResponse(
                empresa.getId(),
                empresa.getNomeEmpresa(),
                empresa.getNipc(),
                empresa.getTelefone(),
                empresa.getMorada(),
                empresa.getCodigoPostal(),
                empresa.getCidade(),
                empresa.getCreatedAt()
        );
    }
}