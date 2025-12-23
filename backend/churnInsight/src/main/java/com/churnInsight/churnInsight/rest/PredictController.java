
package com.churnInsight.churnInsight.rest;

import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.churnInsight.churnInsight.domain.dto.PredictResponse;
import com.churnInsight.churnInsight.service.PredictionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class PredictController {

    private final PredictionService predictionService;

    // Spring inyecta automáticamente el service
    public PredictController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/predict")
    public ResponseEntity<PredictResponse> predict(
            @Valid @RequestBody PredictRequest request) {

        // Delegamos la lógica al service
        PredictResponse response = predictionService.predict(request);

        return ResponseEntity.ok(response);
    }
}
