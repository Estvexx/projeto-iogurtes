package com.empresa.iogurtes.gestaoiogurtes.core.utils;

import com.google.i18n.phonenumbers.*;

public class PhoneUtils {

    private static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    public static String validarENormalizar(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("Telefone obrigatório");
        }

        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(telefone, "PT");
            if (!phoneUtil.isValidNumber(number)) {
                throw new IllegalArgumentException("Número inválido");
            }

            // formato E.164 (+351912345678)
            return phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);

        } catch (NumberParseException e) {
            throw new IllegalArgumentException("Formato de telefone inválido");
        }
    }
}
