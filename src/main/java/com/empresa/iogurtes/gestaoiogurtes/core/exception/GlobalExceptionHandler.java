package com.empresa.iogurtes.gestaoiogurtes.core.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Map<String, Object>> handleBaseException(BaseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "code", ex.getCode(),
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Erro de validação");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "code", "VALIDATION_ERROR",
                "message", message
        ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidFormat(HttpMessageNotReadableException ex) {

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException formatEx) {
            // Erro de enum inválido
            if (formatEx.getTargetType() != null && formatEx.getTargetType().isEnum()) {
                String valoresAceites = Arrays.toString(formatEx.getTargetType().getEnumConstants());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "code", "TURNO_INVALID",
                        "message", "Valor inválido. Valores aceites: " + valoresAceites
                ));
            }

            // Erro de UUID inválido
            if (formatEx.getTargetType() == UUID.class) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "code", "UUID_INVALID",
                        "message", "Formato de UUID inválido"
                ));
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "code", "INVALID_FORMAT",
                "message", "Formato inválido. Verifica os dados enviados."
        ));
    }
}