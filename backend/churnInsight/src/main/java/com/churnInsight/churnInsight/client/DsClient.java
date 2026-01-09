package com.churnInsight.churnInsight.client;
import com.churnInsight.churnInsight.domain.dto.requestToDSDTO.PredictRequestToDS;
import com.churnInsight.churnInsight.exception.DsServiceException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

@Component
public class DsClient {

    // Permite realizar llamadas HTTP al microservicio de Data Science
    private final RestTemplate restTemplate = new RestTemplate();
    // URL del microservicio DS
    private static final String DS_URL = "https://churn-api-v2-0.onrender.com/predict";

    /**
    * Envía los datos del cliente al microservicio de Data Science (FastAPI)
    * y devuelve la respuesta del modelo predictivo en formato JSON genérico.
    */
    public String predict(PredictRequestToDS request) {
        try{        
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PredictRequestToDS> entity =
                    new HttpEntity<>(request, headers);


            ResponseEntity<String> response =
                    restTemplate.postForEntity(DS_URL, entity, String.class);
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
