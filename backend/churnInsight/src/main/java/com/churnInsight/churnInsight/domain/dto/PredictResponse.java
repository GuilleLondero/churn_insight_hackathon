package com.churnInsight.churnInsight.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de salida para la predicción de churn.
 * Devuelve la predicción (texto) y la probabilidad asociada.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictResponse {

    /** Resultado de la predicción (ej: "Va a cancelar" / "Va a continuar") */
    private String prediccion;

    /** Probabilidad de churn (0.0 a 1.0) */
    private Double probabilidadChurn;

    private List<FeatureDTO> topFeatures;

}
