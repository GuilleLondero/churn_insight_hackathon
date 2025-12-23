package com.churnInsight.churnInsight.service;

import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.churnInsight.churnInsight.domain.dto.PredictResponse;
import com.churnInsight.churnInsight.entity.PredictionLog;
import com.churnInsight.churnInsight.repository.PredictionLogRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PredictionService {

    private final PredictionLogRepository predictionLogRepository;

    public PredictionService(PredictionLogRepository predictionLogRepository) {
        this.predictionLogRepository = predictionLogRepository;
    }

    /**
     * MVP: por ahora devuelve una respuesta "stub" y guarda un log en BD.
     * Luego aquí se llamará al DS Client.
     */
    public PredictResponse predict(PredictRequest request) {

        // 1) Stub temporal (luego se reemplaza por la respuesta real de DS)
        PredictResponse response = new PredictResponse("No cancelará", 0.35);

        // 2) Construir log
        PredictionLog log = new PredictionLog();
        log.setTimestamp(Instant.now());
        log.setPrediccion(response.getPrediccion());
        log.setProbabilidadChurn(response.getProbabilidadChurn());
        log.setModeloVersion("stub-v0");
        log.setStatus("OK");

        // 3) Guardar en Postgres
        predictionLogRepository.save(log);

        return response;
    }
}