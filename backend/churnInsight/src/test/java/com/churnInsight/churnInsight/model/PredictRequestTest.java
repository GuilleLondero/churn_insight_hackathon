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
        dto.setPlan("Basic");
        //dto.setValorPlan(100.0);
        dto.setMetodoPago("tarjeta");
        dto.setFacturasImpagas(0);
        dto.setFrecuenciaUso(10);
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
    public void validaValorPlanNull(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        //dto.setValorPlan(null);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("valorPlan"));
    }

    @Test
    public void validaValorPlanNegativo(){
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        //dto.setValorPlan(-2.0);
        violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("valorPlan"));
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
}
