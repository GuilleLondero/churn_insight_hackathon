package com.churnInsight.churnInsight.domain.dto;

import java.util.List;

import com.churnInsight.churnInsight.domain.dto.requestToDSDTO.Prediccion;
import com.fasterxml.jackson.annotation.JsonAlias;

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
    @JsonAlias("prediccion")
    private Prediccion prediccion;

    /** Probabilidad de churn (0.0 a 1.0) */
    @JsonAlias("probabilidad_churn")
    private Float probabilidadChurn;

    @JsonAlias("umbral_decision")
    private Float umbralDecicion;

    @JsonAlias("top_features")
    private List<TopFeatureDto> topFeatures;

    @JsonAlias("modelo_version")
    private String modeloVersion;

}
