package com.churnInsight.churnInsight.service;

import com.churnInsight.churnInsight.client.DsClient;
import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.churnInsight.churnInsight.domain.dto.PredictResponse;
import com.churnInsight.churnInsight.domain.dto.TopFeatureDto;
import com.churnInsight.churnInsight.entity.PredictionLog;
import com.churnInsight.churnInsight.exception.DsServiceException;
import com.churnInsight.churnInsight.repository.PredictionLogRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PredictionService {

    private final PredictionLogRepository predictionLogRepository;
    private final DsClient dsClient;

    public PredictionService(PredictionLogRepository predictionLogRepository, DsClient dsClient) {
        this.predictionLogRepository = predictionLogRepository;
        this.dsClient = dsClient;
    }

    public PredictResponse predict(PredictRequest request, String nombreUsuario) {

        PredictionLog log = new PredictionLog();
        log.setTimestamp(Instant.now());
        log.setUsuario(nombreUsuario);

        try {
            Map<String, Object> dsResponse = dsClient.predict(request);

            // ✅ Lee camelCase o snake_case (por si DS aún no cambió)
            String prediccion = (String) dsResponse.get("prediccion");

            Number probNum = (Number) (dsResponse.containsKey("probabilidadChurn")
                    ? dsResponse.get("probabilidadChurn")
                    : dsResponse.get("probabilidad_churn"));
            double probabilidad = (probNum != null) ? probNum.doubleValue() : 0.0;

            Object modeloVersionObj = dsResponse.containsKey("modeloVersion")
                    ? dsResponse.get("modeloVersion")
                    : dsResponse.get("modelo_version");
            String modeloVersion = (modeloVersionObj != null) ? modeloVersionObj.toString() : "v1";

            Object topFeaturesObj = dsResponse.containsKey("topFeatures")
                    ? dsResponse.get("topFeatures")
                    : dsResponse.get("top_features");

            List<TopFeatureDto> topFeatures = new ArrayList<>();
            if (topFeaturesObj instanceof List<?> list) {
                for (Object item : list) {
                    if (item instanceof Map<?, ?> itemMap) {
                        String feature = (String) itemMap.get("feature");
                        Object valor = itemMap.get("valor");
                        String impacto = (String) itemMap.get("impacto");
                        topFeatures.add(new TopFeatureDto(feature, valor, impacto));
                    }
                }
            }

            PredictResponse response = new PredictResponse(
                    prediccion,
                    probabilidad,
                    topFeatures,
                    modeloVersion
            );

            // Log OK
            log.setPrediccion(prediccion);
            log.setProbabilidadChurn(probabilidad);
            log.setModeloVersion(modeloVersion);
            log.setStatus("OK");
            log.setErrorMessage(null);

            predictionLogRepository.save(log);

            return response;

        } catch (DsServiceException ex) {
            // Log ERROR + re-lanzar para que GlobalExceptionHandler devuelva 503
            log.setPrediccion("Predicción no disponible");
            log.setProbabilidadChurn(0.0);
            log.setModeloVersion("ds-error");
            log.setStatus("ERROR");
            log.setErrorMessage(ex.getMessage());
            predictionLogRepository.save(log);

            throw ex;
        }
    }
}