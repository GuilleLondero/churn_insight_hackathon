package com.churnInsight.churnInsight.client;
import com.churnInsight.churnInsight.domain.dto.requestToDSDTO.PredictRequestToDS;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DsClient {

    // Permite realizar llamadas HTTP (POST) al servicio de Data Science
    private final RestTemplate restTemplate = new RestTemplate();

    // URL del microservicio DS
    private static final String DS_URL = "https://churn-api-v2-0.onrender.com/predict";

    /**
    * Envía los datos del cliente al microservicio de Data Science (FastAPI)
    * y devuelve la respuesta del modelo predictivo en formato JSON genérico.
    */
    public String predict(PredictRequestToDS request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PredictRequestToDS> entity =
                new HttpEntity<>(request, headers);


        ResponseEntity<String> response =
                restTemplate.postForEntity(DS_URL, entity, String.class);
        return response.getBody();
    }
}