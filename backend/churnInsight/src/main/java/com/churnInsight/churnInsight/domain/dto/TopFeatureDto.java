package com.churnInsight.churnInsight.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopFeatureDto{

    private String feature;   // nombre de la variable
    private Object valor;     // puede ser int, string, etc.
    private String impacto;   // "Reduce el riesgo" / "Aumenta el riesgo"
}