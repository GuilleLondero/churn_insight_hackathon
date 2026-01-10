package com.churnInsight.churnInsight.domain.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para la predicción de churn.
 * Representa los datos del cliente que llegan en el POST /predict.
 */

@Data
@NoArgsConstructor
public class PredictRequest {
   
    /*Antigüedad del cliente en meses */
    @NotNull
    @Min(0)
    private Integer antiguedad;

    /** Tipo de plan contratado (ej: Basic, Premium) */
    @NotBlank
    private String plan;

    /** Método de pago utilizado por el cliente (ej: tarjeta, débito) */
    @NotBlank
    private String metodoPago;

    /** Cantidad de facturas impagas */ 
    @NotNull
    @Min(0)
    private Integer facturasImpagas;

    /** Frecuencia de uso del servicio (ej: número de accesos por mes) */
    @NotNull
    @Min(0)
    private Integer frecuenciaUso; 

    @NotNull
    @Min(0)
    @Max(50)
    private Integer ticketsSoporte;

    @NotBlank
    private String tipoContrato;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer cambiosPlan;

    @NotBlank
    private String canalAdquisicion;

}
