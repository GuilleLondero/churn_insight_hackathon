package com.churnInsight.churnInsight.client;

import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class DsClient {

    // Permite realizar llamadas HTTP (POST) al servicio de Data Science
    private final RestTemplate restTemplate = new RestTemplate();

    // URL del microservicio DS
    private static final String DS_URL = "http://localhost:8000/predict";

    /**
    * Envía los datos del cliente al microservicio de Data Science (FastAPI)
    * y devuelve la respuesta del modelo predictivo en formato JSON genérico.
    */
    public Map<String, Object> predict(PredictRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PredictRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(DS_URL, entity, Map.class);

        return response.getBody();
    }
}