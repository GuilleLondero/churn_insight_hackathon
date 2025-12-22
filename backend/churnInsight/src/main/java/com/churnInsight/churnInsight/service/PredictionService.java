package com.churnInsight.churnInsight.service;

import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.churnInsight.churnInsight.domain.dto.PredictResponse;
import com.churnInsight.churnInsight.exception.DsServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;

@Service
public class PredictionService {

    // URL temporal del equipo de Data Science (esto luego puede ir en application.properties)
    // Si ellos usan Flask, suele ser puerto 5000. Si usan FastAPI, 8000.
    private final String PYTHON_API_URL = "http://localhost:5000/predict";
    private final RestTemplate restTemplate;

    public PredictionService() {
        this.restTemplate = new RestTemplate();
    }

    public PredictResponse obtenerPrediccion(PredictRequest request) {
        try {
            // 1. Intentamos llamar a la API de Python
            return restTemplate.postForObject(PYTHON_API_URL, request, PredictResponse.class);

        } catch (ResourceAccessException e) {
            // 2. Si Python está apagado o no responde, lanzamos la excepción que
            // tus compañeros crearon en el GlobalExceptionHandler.
            throw new DsServiceException("No se pudo conectar con el motor de IA. ¿Está corriendo Python?");

        } catch (Exception e) {
            // 3. Cualquier otro error raro
            throw new DsServiceException("Error inesperado al procesar la predicción: " + e.getMessage());
        }
    }
}