package com.churnInsight.churnInsight.rest;

import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.churnInsight.churnInsight.domain.dto.PredictResponse;
import com.churnInsight.churnInsight.service.PredictionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
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

    @PostMapping(value = "/batch-predict",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<PredictResponse>> batchPrediction(@RequestParam("file") MultipartFile file) {
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        List<PredictResponse> response = predictionService.batchPrediction(file, nombreUsuario);

        return ResponseEntity.ok(response);
    }
    
}