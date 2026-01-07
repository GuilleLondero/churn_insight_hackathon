package com.churnInsight.churnInsight.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de salida para la predicción de churn.
 * Devuelve resultado, probabilidad y explicación del modelo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictResponse {

    /** Resultado de la predicción */
    private String prediccion;

    /** Probabilidad de churn (0.0 a 1.0) */
    private Double probabilidadChurn;

    /** Top 3 variables más influyentes del modelo */
    private List<TopFeatureDto> topFeatures;

    /** Versión del modelo ML */
    private String modeloVersion;
}