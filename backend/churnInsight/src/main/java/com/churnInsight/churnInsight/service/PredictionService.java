package com.churnInsight.churnInsight.service;

import com.churnInsight.churnInsight.client.DsClient;
import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.churnInsight.churnInsight.domain.dto.PredictResponse;
import com.churnInsight.churnInsight.entity.PredictionLog;
import com.churnInsight.churnInsight.repository.PredictionLogRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class PredictionService {

    private final PredictionLogRepository predictionLogRepository;
    private final DsClient dsClient;

    public PredictionService(PredictionLogRepository predictionLogRepository,
                             DsClient dsClient) {
        this.predictionLogRepository = predictionLogRepository;
        this.dsClient = dsClient;
    }

    public PredictResponse predict(PredictRequest request, String nombreUsuario) {

        PredictionLog log = new PredictionLog();
        log.setTimestamp(Instant.now());

        try {
            // 1) Llamar a Data Science (FastAPI)
            Map<String, Object> dsResponse = dsClient.predict(request);

            // 2) Convertir respuesta DS -> PredictResponse
            String prediccion = (String) dsResponse.get("prediccion");

            Number probNum = (Number) dsResponse.get("probabilidad_churn");
            double probabilidad = (probNum != null) ? probNum.doubleValue() : 0.0;

            PredictResponse response =
                    new PredictResponse(prediccion, probabilidad);

            // 3) Guardar log OK en Postgres
            log.setPrediccion(prediccion);
            log.setProbabilidadChurn(probabilidad);

            Object modeloVersion = dsResponse.get("modelo_version");
            log.setModeloVersion(
                    modeloVersion != null ? modeloVersion.toString() : "v1"
            );

            log.setStatus("OK");
            log.setErrorMessage(null);
            
            // El requisito clave: Guardar el usuario
            log.setUsuario(nombreUsuario);

            predictionLogRepository.save(log);

            return response;

        } catch (Exception ex) {
            // 4) Fallback si DS no está disponible
            log.setPrediccion("Predicción no disponible");
            log.setProbabilidadChurn(0.0);
            log.setModeloVersion("ds-error");
            log.setStatus("ERROR");
            log.setErrorMessage("DS no disponible: " + ex.getMessage());

            predictionLogRepository.save(log);

            return new PredictResponse("Predicción no disponible", 0.0);
        }
    }
}