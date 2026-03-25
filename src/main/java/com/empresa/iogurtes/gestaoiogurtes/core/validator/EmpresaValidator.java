package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.repository.EmpresaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.utils.PhoneUtils;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class EmpresaValidator {

    private final EmpresaRepository empresaRepository;

    public EmpresaValidator(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public void validateCreateEmpresa(String nomeEmpresa,
                                      String nipc,
                                      String morada,
                                      String codigoPostal,
                                      String cidade) {

        validarNomeEmpresa(nomeEmpresa);
        validarNipc(nipc);
        validarMorada(morada);
        validarCodigoPostal(codigoPostal);
        validarCidade(cidade);
    }

    public void validateUpdateEmpresa(UUID id,
                                      String nomeEmpresa,
                                      String nipc,
                                      String morada,
                                      String codigoPostal,
                                      String cidade) {

        validarNomeEmpresa(nomeEmpresa);
        validarNipcUpdate(id, nipc);
        validarMorada(morada);
        validarCodigoPostal(codigoPostal);
        validarCidade(cidade);
    }

    private void validarNomeEmpresa(String nomeEmpresa) {
        if (nomeEmpresa == null || nomeEmpresa.isBlank()) {
            throw new IllegalArgumentException("Nome da empresa é obrigatório");
        }
        if (nomeEmpresa.length() < 3 || nomeEmpresa.length() > 60) {
            throw new IllegalArgumentException("Nome da empresa deve ter entre 3 e 60 caracteres");
        }
    }

    private void validarNipc(String nipc) {
        if (nipc == null || nipc.isBlank()) {
            throw new IllegalArgumentException("NIPC é obrigatório");
        }

        if (!nipc.matches("^\\d{9}$")) {
            throw new IllegalArgumentException("NIPC deve conter exatamente 9 dígitos numéricos");
        }
        if (empresaRepository.existsByNipc(nipc)) {
            throw new IllegalArgumentException("Já existe uma empresa com este NIPC");
        }
    }

    public String validarTelefone(String telefone) {
        return PhoneUtils.validarENormalizar(telefone);
    }

    private void validarMorada(String morada) {
        if (morada == null || morada.isBlank()) {
            return; // Opcional
        }
        if (morada.length() > 200) {
            throw new IllegalArgumentException("Morada não pode exceder 200 caracteres");
        }
    }

    private void validarCodigoPostal(String codigoPostal) {
        if (codigoPostal == null || codigoPostal.isBlank()) {
            return;
        }
        if (!codigoPostal.matches("^\\d{4}-\\d{3}$")) {
            throw new IllegalArgumentException(
                    "Código postal inválido. Formato: 1234-567"
            );
        }
    }

    private void validarCidade(String cidade) {
        if (cidade == null || cidade.isBlank()) {
            return;
        }
        if (cidade.length() > 100) {
            throw new IllegalArgumentException("Cidade não pode exceder 100 caracteres");
        }
    }
    // Validação para o update
    private void validarNipcUpdate(UUID id, String nipc) {

        if (nipc == null || nipc.isBlank()) {
            throw new IllegalArgumentException("NIPC é obrigatório");
        }

        if (!nipc.matches("^\\d{9}$")) {
            throw new IllegalArgumentException("NIPC deve conter exatamente 9 dígitos numéricos");
        }

        if (empresaRepository.existsByNipcAndIdNot(nipc, id)) {
            throw new IllegalArgumentException("Já existe outra empresa com este NIPC");
        }
    }
}