package com.churnInsight.churnInsight.integration;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@Service
public class PythonService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String PYTHON_API_URL = "http://localhost:8000/predict";

    public String obtenerPrediccion(Map<String, Object> datosCliente) {
        try {
            ResponseEntity<String> respuesta = restTemplate.postForEntity(PYTHON_API_URL, datosCliente, String.class);
            return respuesta.getBody();
        } catch (Exception e) {
            return "Error al conectar con Python: " + e.getMessage();
        }
    }
}
