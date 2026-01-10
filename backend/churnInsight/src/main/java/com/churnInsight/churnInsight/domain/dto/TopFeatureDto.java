package com.churnInsight.churnInsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopFeatureDto{
    
    @JsonAlias("feature")
    private String feature; // nombre de la variable

    @JsonAlias("valor_cliente")
    private String valor; // valor que tiene el cliente
    
    @JsonAlias("impacto")
    private String impacto; //nivel de impacto : alto_riesgo, medio_riesgo, bajo_riesgo
    
    @JsonAlias("importancia")
    private Float importancia; //importancia de la feature en el modelo
}