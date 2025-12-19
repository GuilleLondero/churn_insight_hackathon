package com.churnInsight.churnInsight.rest;

// DTOs de entrada y salida 
import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.churnInsight.churnInsight.domain.dto.PredictResponse;


// Validación y comunicación Frontend
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Devuelve JSON
@RestController


// Dirección: http://localhost:8080/api/v1
@RequestMapping("/api/v1")

// Conexion Frontend
@CrossOrigin(origins = "*")
public class PredictController {



    
     //Endpoint temporal para probar la comunicación.
    @PostMapping("/predict")
    public ResponseEntity<PredictResponse> predict(@Valid @RequestBody PredictRequest request) {


        // ESPACIO PARA EL SERVICE - comunicacion con modelo Python
    
        
        // Código 200 OK
        return ResponseEntity.ok(null);
    }
}