package com.churnInsight.churnInsight.client;

import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.churnInsight.churnInsight.exception.DsServiceException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

import java.util.Map;

@Component
public class DsClient {

    // Permite realizar llamadas HTTP al microservicio de Data Science
    private final RestTemplate restTemplate = new RestTemplate();

    // URL del microservicio DS (FastAPI)
    private static final String DS_URL = "http://localhost:8000/predict";

    /**
     * Envía el request a DS y devuelve el JSON de respuesta.
     * Si DS no responde o devuelve error, lanzamos DsServiceException
     * para que GlobalExceptionHandler responda con 503 (servicio no disponible).
     */
    public Map<String, Object> predict(PredictRequest request) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PredictRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(DS_URL, entity, Map.class);

            // Si DS respondió pero sin body (raro), lo tratamos como error
            if (response.getBody() == null) {
                throw new DsServiceException("DS respondió sin body");
            }

            return response.getBody();

        } catch (ResourceAccessException ex) {
            // Ej: DS caído, connection refused, timeout
            throw new DsServiceException("No se pudo conectar a DS", ex);

        } catch (HttpStatusCodeException ex) {
            // Ej: DS respondió 400/500 con body
            throw new DsServiceException("DS respondió con error HTTP: " + ex.getStatusCode(), ex);

        } catch (RestClientException ex) {
            // Cualquier otro error de RestTemplate
            throw new DsServiceException("Error llamando a DS", ex);
        }
    }
}
