package com.empresa.iogurtes.gestaoiogurtes.core.utils;

import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationException;
import com.google.i18n.phonenumbers.*;

import java.util.Set;

public class PhoneUtils {

    private static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    // Países permitidos
    private static final Set<String> PAISES_PERMITIDOS = Set.of("PT", "ES", "FR");

    public static String validarENormalizar(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new ValidationException(ValidationErrorCode.TELEFONE_NULL);
        }

        try {
            // Tenta parse sem país fixo — deteta automaticamente se tiver +351/+33/+34
            Phonenumber.PhoneNumber number = phoneUtil.parse(telefone, null);

            if (!phoneUtil.isValidNumber(number)) {
                throw new ValidationException(ValidationErrorCode.TELEFONE_INVALID);
            }

            // Verifica se o país é permitido
            String codigoPais = phoneUtil.getRegionCodeForNumber(number);
            if (!PAISES_PERMITIDOS.contains(codigoPais)) {
                throw new ValidationException(ValidationErrorCode.TELEFONE_PAIS_NAO_PERMITIDO);
            }

            return phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);

        } catch (NumberParseException e) {
            // Se não tiver indicativo, tenta como PT (default)
            try {
                Phonenumber.PhoneNumber number = phoneUtil.parse(telefone, "PT");
                if (phoneUtil.isValidNumber(number)) {
                    return phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
                }
            } catch (NumberParseException ignored) {}

            throw new ValidationException(ValidationErrorCode.TELEFONE_INVALID_FORMAT);
        }
    }
}