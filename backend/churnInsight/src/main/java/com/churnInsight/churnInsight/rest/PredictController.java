package com.churnInsight.churnInsight.rest;

import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.churnInsight.churnInsight.domain.dto.PredictResponse;
import com.churnInsight.churnInsight.service.PredictionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://127.0.0.1:5500/", "https://churninsight.netlify.app/"})
public class PredictController {

    private final PredictionService predictionService;

    @PostMapping("/predict")
    public ResponseEntity<PredictResponse> predict(@RequestBody @Valid PredictRequest request) {
        
        // 1. Obtener quién está llamando (El piloto)
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // 2. Realizar la predicción (Usando el nombre REAL de tu servicio)
        PredictResponse response = predictionService.predict(request, nombreUsuario);

        return ResponseEntity.ok(response);
    }
}