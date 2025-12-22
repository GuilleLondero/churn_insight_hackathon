package com.churnInsight.churnInsight.rest;

// DTOs
import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.churnInsight.churnInsight.domain.dto.PredictResponse;

// Importamos TU servicio (Si te sale rojo aquí, es porque falta crear el archivo del paso anterior)
import com.churnInsight.churnInsight.service.PredictionService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class PredictController {

    // 1. Declaramos el servicio
    private final PredictionService predictionService;

    // 2. Constructor: Spring inyecta automáticamente el servicio aquí
    public PredictController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/predict")
    public ResponseEntity<PredictResponse> predict(@Valid @RequestBody PredictRequest request) {

        // 3. ¡Conexión Real! Llamamos al servicio que conecta con Python
        PredictResponse resultado = predictionService.obtenerPrediccion(request);

        // Devolvemos la respuesta real (Previsión y Probabilidad)
        return ResponseEntity.ok(resultado);
    }
}