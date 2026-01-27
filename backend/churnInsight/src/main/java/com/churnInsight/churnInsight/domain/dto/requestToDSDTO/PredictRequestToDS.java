package com.churnInsight.churnInsight.domain.dto.requestToDSDTO;

import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PredictRequestToDS {
  
  @NotNull
  @Min(0)
  @JsonProperty("antiguedad")
  private Integer antiguedad;
  
  @NotNull
    @JsonProperty("plan")
    private Plan plan;

    @NotNull
    @JsonProperty("metodo_pago")
    private MetodoPago metodoPago;

    @NotNull
    @Min(0)
    @JsonProperty("facturas_impagas")
    private Integer facturasImpagas;

    @NotNull
    @Min(0)
    @JsonProperty("frecuencia_uso")
    private Integer frecuenciaUso;

    @NotNull
    @Min(0)
    @JsonProperty("tickets_soporte")
    private Integer ticketsSoporte;

    @NotNull
    @JsonProperty("tipo_contrato")
    private TipoContrato tipoContrato;

    @NotNull
    @Min(0)
    @JsonProperty("cambios_plan")
    private Integer cambiosPlan;

    @NotNull
    @JsonProperty("canal_adquisicion")
    private CanalAdquisicion canalAdquisicion;

  public PredictRequestToDS(PredictRequest request){
    this.antiguedad = request.getAntiguedad();
    this.plan = Plan.valueOf(request.getPlan());
    this.metodoPago = MetodoPago.valueOf(request.getMetodoPago());
    this.facturasImpagas = request.getFacturasImpagas();
    this.frecuenciaUso = request.getFrecuenciaUso();
    this.ticketsSoporte = request.getTicketsSoporte();
    this.tipoContrato = TipoContrato.valueOf(request.getTipoContrato());
    this.cambiosPlan = request.getCambiosPlan();
    this.canalAdquisicion = CanalAdquisicion.valueOf(request.getCanalAdquisicion());
  }

}
