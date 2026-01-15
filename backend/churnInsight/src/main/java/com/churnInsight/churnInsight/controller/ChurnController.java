package com.churnInsight.churnInsight.controller;

import com.churnInsight.churnInsight.integration.PythonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChurnController {

    @Autowired
    private PythonService pythonService;

    @PostMapping("/predict")
    public ResponseEntity<String> predecirChurn(@RequestBody Map<String, Object> datosCliente) {
        System.out.println("Recibiendo datos del cliente: " + datosCliente);
        String respuesta = pythonService.obtenerPrediccion(datosCliente);
        return ResponseEntity.ok(respuesta);
    }
}
