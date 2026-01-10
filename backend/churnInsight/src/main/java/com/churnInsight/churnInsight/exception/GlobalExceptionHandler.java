package com.churnInsight.churnInsight.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        List<ApiError.ApiFieldError> detalles = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ApiError.ApiFieldError(err.getField(), err.getDefaultMessage()))
                .toList();

        ApiError body = new ApiError("Validación fallida", detalles, Instant.now());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleValidation(ConstraintViolationException ex) {
        List<ApiError.ApiFieldError> detalles = ex.getConstraintViolations()
                .stream()
                .map(err -> new ApiError.ApiFieldError(err.getMessageTemplate(), err.getMessage()))
                .toList();

        ApiError body = new ApiError("Validación fallida", detalles, Instant.now());
        return ResponseEntity.badRequest().body(body);
    }


    @ExceptionHandler(DsServiceException.class)
    public ResponseEntity<ApiError> handleDs(DsServiceException ex) {
        log.error("Error llamando a DS: {}", ex.getMessage(), ex);

        ApiError body = new ApiError(
                "Servicio de predicción no disponible",
                List.of(new ApiError.ApiFieldError("ds", ex.getMessage())),
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        // ✅ ESTO hará que veas el error real en la terminal
        log.error("Error inesperado en la API", ex);

        ApiError body = new ApiError(
                "Error interno",
                List.of(new ApiError.ApiFieldError("server", "Ocurrió un error inesperado")),
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
