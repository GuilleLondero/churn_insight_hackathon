package com.churnInsight.churnInsight.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
@Table(name = "prediction_log")
public class PredictionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha/hora en que se hizo la predicción
    @Column(nullable = false)
    private Instant timestamp;

    // Resultado de la predicción (ej: "Va a cancelar" / "No cancelará")
    @Column(nullable = false)
    private String prediccion;

    // Probabilidad de churn (0.0 - 1.0)
    @Column(nullable = false)
    private Double probabilidadChurn;

    // Versión del modelo (ej: "v1.0") - útil para auditoría
    private String modeloVersion;

    // Estado del registro: OK / ERROR
    @Column(nullable = false)
    private String status;

    // Mensaje de error (solo si status = ERROR)
    @Column(length = 1000)
    private String errorMessage;
}

