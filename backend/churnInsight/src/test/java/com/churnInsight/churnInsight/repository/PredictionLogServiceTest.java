package com.churnInsight.churnInsight.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.churnInsight.churnInsight.domain.dto.PredictResponse;
import com.churnInsight.churnInsight.service.PredictionService;

public class PredictionLogServiceTest {

    private static PredictionService predictionService;
    private PredictRequest request;

    @BeforeEach
    void setup(){
        crearPredictRequestValido();
    }

    public void crearPredictRequestValido(){
            //Seteo de ejemplo, una vez conectado con el modelo se 
            //adaptara a un escenario real.

            request.setAntiguedad(30); //not null, min 0
            request.setPlan("Basico"); //not blank
            request.setValorPlan(30.0); // not null, min 0
            request.setMetodoPago("Debito automatico"); //not blank
            request.setFacturasImpagas(0); //not null, min 0
            request.setFrecuenciaUso(40); //not null, min 0
    }

    @Test
    public void registroDeLogCorrecto(){
        PredictResponse response = predictionService.predict(request, "Marcelo");
        //despues de esta linea, chequea si retorna el resultado esperado.
    }

    @Test
    public void requestConAntiguedadInvalida(){
        //Valor null no aceptado
        request.setAntiguedad(null);
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, "Marcelo"));
        //valor menor a 0 no aceptado
        request.setAntiguedad(-3);
        assertThrows(MethodArgumentNotValidException.class, () ->  predictionService.predict(request, "Marcelo"));

    }

    @Test
    public void requestConPlanInvalido(){
        //valor string en blanco no aceptado
        request.setPlan(" ");
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, "Marcelo"));
        //valor string vacio no aceptado
        request.setPlan("");
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, "Marcelo"));
        //valor null no aceptado
        request.setPlan(null);
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, "Marcelo"));
    }

    @Test
    public void requestConValorPlanInvalido(){
        //valor null no aceptado
        request.setValorPlan(null);
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, "Marcelo"));
        //valor negativo no aceptado
        request.setValorPlan(-3.0);
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, "Marcelo"));
    }


    @Test
    public void requestConMetodoDePagoInvalido(){
        //valor string en blanco no aceptado
        request.setMetodoPago(" ");
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, "Marcelo"));
        //valor string vacio no aceptado
        request.setMetodoPago("");
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, "Marcelo"));
        //valor null no aceptado
        request.setMetodoPago(null);
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, "Marcelo"));
    }

    @Test
    public void requestConFacturasImpagasInvalidas(){
        //valor null no aceptado
        request.setFacturasImpagas(null);
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, "Marcelo"));
        //valor negativo no aceptado
        request.setFacturasImpagas(-3);
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, "Marcelo"));
    }

    @Test
    public void requestConFrecuenciaUsoInvalido(){
        //valor null no aceptado
        request.setFrecuenciaUso(null);
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, "Marcelo"));
        //valor negativo no aceptado
        request.setFrecuenciaUso(-3);
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, "Marcelo"));
    }

    @Test
    public void requestConUsuarioInvalido(){
        //valor string en blanco no aceptado
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, " "));
        //valor string vacio no aceptado
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, ""));
        //valor null no aceptado
        assertThrows(MethodArgumentNotValidException.class, () -> predictionService.predict(request, null));
    }

}
