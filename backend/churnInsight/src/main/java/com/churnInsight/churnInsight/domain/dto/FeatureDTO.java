package com.churnInsight.churnInsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureDTO {

    @JsonAlias("feature")
    private String feature;
    @JsonAlias("valor_cliente")
    private String valor;
    @JsonAlias("impacto")
    private String impacto;
    @JsonAlias("importancia")
    private Float importancia;

}
