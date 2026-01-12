package com.churnInsight.churnInsight.domain.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Max(120)
    private Integer antiguedad;

    /** Tipo de plan contratado (ej: Basic, Premium) */
    @NotBlank
    @Pattern(regexp = "basico|estandar|premium", message = "Planes validos: basico, estandar o premium")
    private String plan;

    /** Método de pago utilizado por el cliente (ej: tarjeta, débito) */
    @NotBlank
    @Pattern(regexp = "transferencia_bancaria|tarjeta_credito|tarjeta_debito", 
             message = "Metodos de pago validos: transferencia_bancaria, tarjeta_credito, tarjeta_debito")
    private String metodoPago;

    /** Cantidad de facturas impagas */ 
    @NotNull
    @Min(0)
    @Max(10)
    private Integer facturasImpagas;

    /** Frecuencia de uso del servicio (ej: número de accesos por mes) */
    @NotNull
    @Min(0)
    @Max(30)
    private Integer frecuenciaUso; 

    @NotNull
    @Min(0)
    @Max(50)
    private Integer ticketsSoporte;

    @NotBlank
    @Pattern(regexp = "mensual|anual", message = "Planes validos: mensual, anual")
    private String tipoContrato;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer cambiosPlan;

    @NotBlank
    @Pattern(regexp = "web|referido|redes_sociales",
             message ="Canales de adquisicion validos : web, referido, redes_sociales")
    private String canalAdquisicion;

}
