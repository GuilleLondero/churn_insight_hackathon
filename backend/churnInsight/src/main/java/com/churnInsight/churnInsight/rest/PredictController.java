package com.churnInsight.churnInsight.rest;

import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.churnInsight.churnInsight.domain.dto.PredictResponse;
import com.churnInsight.churnInsight.entity.PredictionLog;
import com.churnInsight.churnInsight.repository.PredictionLogRepository;
import com.churnInsight.churnInsight.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant; // <--- Importamos el reloj correcto (Instant)

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PredictController {

    private final PredictionService predictionService;
    private final PredictionLogRepository logRepository;

    @PostMapping("/predict")
    public ResponseEntity<PredictResponse> predict(@RequestBody PredictRequest request) {
        
        // 1. Obtener quién está llamando (El piloto)
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // 2. Realizar la predicción (Usando el nombre REAL de tu servicio)
        PredictResponse response = predictionService.predict(request);

        // 3. Guardar en el Log con el nombre del usuario
        // Nota: Tu servicio también guarda un log, así que aquí estamos creando un segundo registro
        // específico que incluye al usuario, tal como te pidieron.
        PredictionLog log = new PredictionLog();
        log.setModeloVersion("stub-v0"); // Usando el mismo nombre que vi en tu servicio
        
        // Usando los Getters REALES (en español) de tu DTO
        log.setPrediccion(response.getPrediccion());
        log.setProbabilidadChurn(response.getProbabilidadChurn());
        
        // Usando el reloj correcto
        log.setTimestamp(Instant.now());
        log.setStatus("OK"); 
        
        // El requisito clave: Guardar el usuario
        log.setUsuario(nombreUsuario);

        logRepository.save(log);

        return ResponseEntity.ok(response);
    }
}