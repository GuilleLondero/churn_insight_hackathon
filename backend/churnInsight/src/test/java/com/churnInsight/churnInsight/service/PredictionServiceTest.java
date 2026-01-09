package com.churnInsight.churnInsight.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.churnInsight.churnInsight.domain.dto.PredictResponse;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class PredictionServiceTest {
    @Autowired    
    private PredictionService predictionService;
    private PredictRequest pRequest;

    @BeforeEach
    void setup(){
        pRequest = crearPredictRequestValido(3,"basico", "tarjeta_credito",0, 10, 3,"mensual", 0, "web");
    }

    public PredictRequest crearPredictRequestValido(Integer antiguedad, String plan, String metodoPago, Integer factImp, Integer frecUso, 
                                          Integer ticketsSop, String tipoContrato, Integer cambPlan, String canalAdq
    ){
        PredictRequest request = new PredictRequest();
        request.setAntiguedad(antiguedad);
        request.setPlan(plan);
        request.setMetodoPago(metodoPago);
        request.setFacturasImpagas(factImp);
        request.setFrecuenciaUso(frecUso);
        request.setTicketsSoporte(ticketsSop);
        request.setTipoContrato(tipoContrato);
        request.setCambiosPlan(cambPlan);
        request.setCanalAdquisicion(canalAdq);
        return request;
    }

    @Test
    public void registroDeLogCorrecto(){
        assertNotNull(predictionService.predict(pRequest, "Marcelo"));
        //despues de esta linea, chequea si retorna el resultado esperado.
    }

    @Test
    public void requestConAntiguedadInvalida(){
        //Valor null no aceptado
        pRequest.setAntiguedad(null);
        assertThrows(ConstraintViolationException.class, () -> predictionService.predict(pRequest, "Marcelo"));
        //valor menor a 0 no aceptado
        pRequest.setAntiguedad(-3);
        assertThrows(ConstraintViolationException.class, () ->  predictionService.predict(pRequest, "Marcelo"));

    }

    @Test
    public void requestConPlanInvalido(){
        //valor string en blanco no aceptado
        pRequest.setPlan(" ");
        assertThrows(ConstraintViolationException.class, () -> predictionService.predict(pRequest, "Marcelo"));
        //valor string vacio no aceptado
        pRequest.setPlan("");
        assertThrows(ConstraintViolationException.class, () -> predictionService.predict(pRequest, "Marcelo"));
        //valor null no aceptado
        pRequest.setPlan(null);
        assertThrows(ConstraintViolationException.class, () -> predictionService.predict(pRequest, "Marcelo"));
    }

    @Test
    public void requestConMetodoDePagoInvalido(){
        //valor string en blanco no aceptado
        pRequest.setMetodoPago(" ");
        assertThrows(ConstraintViolationException.class, () -> predictionService.predict(pRequest, "Marcelo"));
        //valor string vacio no aceptado
        pRequest.setMetodoPago("");
        assertThrows(ConstraintViolationException.class, () -> predictionService.predict(pRequest, "Marcelo"));
        //valor null no aceptado
        pRequest.setMetodoPago(null);
        assertThrows(ConstraintViolationException.class, () -> predictionService.predict(pRequest, "Marcelo"));
    }

    @Test
    public void requestConFacturasImpagasInvalidas(){
        //valor null no aceptado
        pRequest.setFacturasImpagas(null);
        assertThrows(ConstraintViolationException.class, () -> predictionService.predict(pRequest, "Marcelo"));
        //valor negativo no aceptado
        pRequest.setFacturasImpagas(-3);
        assertThrows(ConstraintViolationException.class, () -> predictionService.predict(pRequest, "Marcelo"));
    }

    @Test
    public void requestConFrecuenciaUsoInvalido(){
        //valor null no aceptado
        pRequest.setFrecuenciaUso(null);
        assertThrows(ConstraintViolationException.class, () -> predictionService.predict(pRequest, "Marcelo"));
        //valor negativo no aceptado
        pRequest.setFrecuenciaUso(-3);
        assertThrows(ConstraintViolationException.class, () -> predictionService.predict(pRequest, "Marcelo"));
    }

    @Test
    public void requestConUsuarioInvalido(){
        //valor string en blanco no aceptado
        assertThrows(ConstraintViolationException.class, () -> predictionService.predict(pRequest, " "));
        //valor string vacio no aceptado
        assertThrows(ConstraintViolationException.class, () -> predictionService.predict(pRequest, ""));
        //valor null no aceptado
        assertThrows(ConstraintViolationException.class, () -> predictionService.predict(pRequest, null));
    }
    //######################## Test casos riesgo alto, riesgo medio y riesgo bajo (casos planteados por el equipo de Data Science)########################
    @Test
    public void requestRiesgoAlto(){
        PredictRequest requestRiesgoAlto = crearPredictRequestValido(3, "basico", "transferencia_bancaria",
                                                               3, 5, 8, "mensual", 2, "web");
        PredictResponse respuesta = predictionService.predict(requestRiesgoAlto, "Marcelo");
        
        assertThat(0.5 < respuesta.getProbabilidadChurn()); //Se comprueba que la probabilidad de churn es mayor a 50%
    }
    @Test
    public void requestRiesgoMedio(){
        PredictRequest requestRiesgoMedio = crearPredictRequestValido(18, "estandar", "tarjeta_credito",
                                                               1, 15, 3, "mensual", 1, "web");
        PredictResponse respuesta = predictionService.predict(requestRiesgoMedio, "Marcelo");

        assertThat(0.3 < respuesta.getProbabilidadChurn()); //Se comprueba que la probabilidad de churn es mayor a 30%
        assertThat(0.7 < respuesta.getProbabilidadChurn()); //Se comprueba que la probabilidad de churn es menor a 70%
    }
    @Test
    public void requestRiesgoBajo(){
        PredictRequest requestRiesgoBajo = crearPredictRequestValido(48, "premium", "tarjeta_credito",
                                                               0, 28, 0, "anual", 0, "referido");
        PredictResponse respuesta = predictionService.predict(requestRiesgoBajo, "Marcelo");

        assertThat(0.3 < respuesta.getProbabilidadChurn()); //Se comprueba que la probabilidad de churn es mayor a 30%
    }

}
