package com.churnInsight.churnInsight.domain.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashLogsDTO {
    // Totales generales
    private Integer totalPredicciones;           
    private Date fechaPrimerPrediccion;          

    // Estadísticas de probabilidad
    private Float probabilidadChurnPromedio;     
    private Float valorMinimoProbabilidad;       
    private Float valorMaximoProbabilidad;       

    // Conteo por decisión
    private Integer totalCancelara;             
    private Integer totalNoCancelara;            
    
    // Métricas de calidad y riesgo
    private Integer totalErrorPrediccion;        
    private Integer totalRiesgoAlto;             
    private Integer totalRiesgoMedio;            
    private Integer totalRiesgoBajo;            
}