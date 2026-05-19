package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.CreateEmpresaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.EmpresaResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.UpdateEmpresaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.empresa.EmpresaErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.empresa.EmpresaException;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Empresa;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.EmpresaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.utils.PhoneUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final UserRepository userRepository;

    public EmpresaService(EmpresaRepository empresaRepository,
                          UserRepository userRepository) {
        this.empresaRepository = empresaRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public EmpresaResponse createEmpresa(CreateEmpresaRequest info) {
        if (empresaRepository.existsByNipc(info.nipc()))
            throw new ValidationException(ValidationErrorCode.NIPC_ALREADY_EXISTS);

        String telefone = validarTelefone(info.telefone());

        try {
            Empresa empresa = new Empresa(
                    info.nomeEmpresa(),
                    info.nipc(),
                    telefone,
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

    public Page<EmpresaResponse> findAll(Pageable pageable) {
        return empresaRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public Page<EmpresaResponse> findAllActive(Pageable pageable) {
        return empresaRepository.findAllByIsActiveTrue(pageable)
                .map(this::toResponse);
    }

    public Page<EmpresaResponse> findAllInactive(Pageable pageable) {
        return empresaRepository.findAllByIsActiveFalse(pageable)
                .map(this::toResponse);
    }


    @Transactional
    public EmpresaResponse updateEmpresa(UUID id, UpdateEmpresaRequest info) {
        Empresa empresa = empresaRepository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new EmpresaException(EmpresaErrorCode.EMPRESA_NOT_FOUND));

        if (empresaRepository.existsByNipcAndIdNot(info.nipc(), id))
            throw new ValidationException(ValidationErrorCode.NIPC_ALREADY_EXISTS);

        String telefone = validarTelefone(info.telefone());

        try {
            empresa.setNomeEmpresa(info.nomeEmpresa());
            empresa.setTelefone(telefone);
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
        Empresa empresa = empresaRepository.findByIdAndIsActiveIsTrue(id)
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

    private String validarTelefone(String telefone) {
        return PhoneUtils.validarENormalizar(telefone);
    }
}