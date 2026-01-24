package com.churnInsight.churnInsight.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.churnInsight.churnInsight.exception.ApiError.ApiFieldError;
import com.churnInsight.churnInsight.rest.UsuarioNoEncontradoException;

import jakarta.validation.ConstraintViolationException;

import java.time.Instant;
import java.util.ArrayList;
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleValidation(DataIntegrityViolationException ex) {
        List<ApiError.ApiFieldError> detalles = new ArrayList<>();                
        ApiFieldError apierr = new ApiError.ApiFieldError(ex.getMostSpecificCause().getMessage(), ex.getLocalizedMessage());
        detalles.add(apierr);
        
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleCredentials(BadCredentialsException ex) {

        ApiError body = new ApiError(
                "Error de autenticacion",
                List.of(new ApiError.ApiFieldError(ex.getMessage(), "Los datos de usuario no coinciden!")),
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
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

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ApiError> handleAtriburosErroneos(ObjectOptimisticLockingFailureException ex){
        ApiError body = new ApiError(
                "Error de parametros",
                List.of(new ApiError.ApiFieldError("El json no cumple los requisitos", """
                        Json esperado: usuario : String, password: String, email : String
                        """)),
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<ApiError> handleUsuarioNoEncontrado(UsuarioNoEncontradoException ex){
        ApiError body = new ApiError(
                "Usuario no encontrado",
                List.of(new ApiError.ApiFieldError("Error al encontrar el usuario indicado", ex.getMessage())),
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
