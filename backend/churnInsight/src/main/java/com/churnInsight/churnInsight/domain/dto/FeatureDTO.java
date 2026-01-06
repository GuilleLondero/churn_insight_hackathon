package com.churnInsight.churnInsight.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureDTO {

    private String feature;
    private Integer valor;
    private String impacto;

}
