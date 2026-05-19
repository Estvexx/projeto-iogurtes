package com.empresa.iogurtes.gestaoiogurtes.core.validator;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationException;
import com.empresa.iogurtes.gestaoiogurtes.core.utils.PhoneUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FornecedorValidator {
    public FornecedorValidator() {
    }

    public void validarDatas(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio == null)
            throw new ValidationException(ValidationErrorCode.DATA_INICIO_NULL);
        if (dataFim != null && dataFim.isBefore(dataInicio))
            throw new ValidationException(ValidationErrorCode.DATA_FIM_BEFORE_DATA_INICIO);
    }

    public String validarTelefone(String telefone) {
        return PhoneUtils.validarENormalizar(telefone);
    }
}