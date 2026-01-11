package com.churnInsight.churnInsight.model;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.churnInsight.churnInsight.domain.dto.PredictRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PredictRequestTest {

    private Validator validator;
    private PredictRequest dto = new PredictRequest();
    private Set<ConstraintViolation<PredictRequest>> violations;
    
    @BeforeEach
    void setUp() {
        // Inicializa una implementacion de Bean Validator y
        // carga validadores por defecto definidos por la 
        // especificacion de Jakarta Validation.
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        setearPredictRequestValido();

    }

    private void setearPredictRequestValido(){
        dto.setAntiguedad(3);
        dto.setPlan("basico");
        dto.setMetodoPago("tarjeta_credito");
        dto.setFacturasImpagas(0);
        dto.setFrecuenciaUso(10);
        dto.setTicketsSoporte(3);
        dto.setTipoContrato("mensual");
        dto.setCambiosPlan(0);
        dto.setCanalAdquisicion("web");
    }

    @Test
    public void validaAntiguedadNull(){
        //Primero valido que no hay violaciones en las restricciones
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        //seteo valor no premitido
        dto.setAntiguedad(null);
        //valido que ahora si hay violacion de restricciones
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("antiguedad"));
    }

    @Test
    public void validaAntiguedadMenorA0(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setAntiguedad(-2);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("antiguedad"));
    }
    @Test
    public void validaAntiguedadMayorA120(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setAntiguedad(122);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("antiguedad"));
    }

    @Test
    public void validaPlanStringEnBlanco(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setPlan(" ");
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("plan"));
    }
    
    @Test
    public void validaPlanNull(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setPlan(null);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("plan"));
    }
    
    @Test
    public void validaValoresCorrectosPlan(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setPlan("Super");
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("plan"));
        //valores correctos no lanzan error
        dto.setPlan("basico");
        violations = validator.validate(dto);
        assertThat(violations.isEmpty());
        
        dto.setPlan("estandar");
        violations = validator.validate(dto);
        assertThat(violations.isEmpty());

        dto.setPlan("premium");
        violations = validator.validate(dto);
        assertThat(violations.isEmpty());
    }

    @Test
    public void validarMetodoPagoNull(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setMetodoPago(null);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("metodoPago"));
    }

    @Test
    public void validarMetodoPagoStringVacio(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setMetodoPago(" ");
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("metodoPago"));
    }

    @Test
    public void validarFacturasImpagasNull(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setFacturasImpagas(null);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("facturasImpagas"));
    }

    @Test
    public void validarFacturasImpagasNegativas(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setFacturasImpagas(-3);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("facturasImpagas"));
    }
    
    @Test
    public void validarFacturasImpagasSuperaMaximo(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setFacturasImpagas(11);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("facturasImpagas"));
    }

    @Test
    public void validarFrecuenciaUsoNull(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setFrecuenciaUso(null);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("frecuenciaUso"));
    }

    @Test
    public void validarFrecuenciaUsoNegativa(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setFrecuenciaUso(-3);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("frecuenciaUso"));
    }
    
    @Test
    public void validarFrecuenciaUsoSuperMaximo(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setFrecuenciaUso(31);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("frecuenciaUso"));
    }

    @Test
    public void validarTicketsSoporteNull(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setTicketsSoporte(null);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("ticketsSoporte"));
    }

    @Test
    public void validarTicketsSoporteNegativo(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setTicketsSoporte(-3);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("ticketsSoporte"));
    }

    @Test
    public void validarTicketsSoporteExcedeMaximo(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setTicketsSoporte(51);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("ticketsSoporte"));
    }

    @Test
    public void validarTipoContratoNull(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setTipoContrato(null);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("tipoContrato"));
    }

    @Test
    public void validarTipoContratoStringVacio(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setTipoContrato(" ");
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("tipoContrato"));
    }
    
    @Test
    public void validarTipoContratoValidos(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setTipoContrato("diario");
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("tipoContrato"));

        //Con valores correctos no hay error
        dto.setTipoContrato("mensual");
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setTipoContrato("anual");
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void validarCambiosPlanNull(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setCambiosPlan(null);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("cambiosPlan"));
    }

    @Test
    public void validarCambiosPlanNegativo(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setCambiosPlan(-3);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("cambiosPlan"));
    }

    @Test
    public void validarCambiosPlanExcedeMaximo(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setCambiosPlan(6);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("cambiosPlan"));
    }

    @Test
    public void validarCanalAdquisicionNull(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setCanalAdquisicion(null);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("canalAdquisicion"));
    }

    @Test
    public void validarCanalAdquisicionStringVacio(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setCanalAdquisicion(" ");
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("canalAdquisicion"));
    }
    @Test
    public void validarCanalesAdquisicionValidos(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setCanalAdquisicion("publicidad");
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("canalAdquisicion"));

        //Con valores correctos no hay error
        dto.setCanalAdquisicion("web");
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setCanalAdquisicion("referido");
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        dto.setCanalAdquisicion("redes_sociales");
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

}
