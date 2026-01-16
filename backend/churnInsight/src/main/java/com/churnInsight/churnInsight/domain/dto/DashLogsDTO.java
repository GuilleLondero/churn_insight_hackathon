package com.churnInsight.churnInsight.domain.dto;

import java.time.Instant;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashLogsDTO {
    // Totales generales
    private Long totalPredicciones;           
    private Instant fechaPrimerPrediccion;          

    // Estadísticas de probabilidad
    private Double probabilidadChurnPromedio;     
    private Float valorMinimoProbabilidad;       
    private Float valorMaximoProbabilidad;       

    // Conteo por decisión
    private Long totalCancelara;             
    private Long totalNoCancelara;            
    
    // Métricas de calidad y riesgo
    private Long totalErrorPrediccion;        
    private Long totalRiesgoAlto;             
    private Long totalRiesgoMedio;            
    private Long totalRiesgoBajo;  
}