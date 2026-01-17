package com.churnInsight.churnInsight.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.churnInsight.churnInsight.domain.dto.DashLogsDTO;
import com.churnInsight.churnInsight.domain.dto.FechasLimiteDTO;
import com.churnInsight.churnInsight.entity.PredictionLog;
import com.churnInsight.churnInsight.service.DashboardService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/logs")
@CrossOrigin(origins = {"http://127.0.0.1:5500/", "https://churninsight.netlify.app/"})
public class AdminLogController {

    @Autowired
    private DashboardService dashboardService;

    // 1. Ver estadísticas en general
    // Endpoint: GET /logs
    @GetMapping
    public ResponseEntity<DashLogsDTO> obtenerLogsGlobales() {
        // Llamamos al servicio con null 
        DashLogsDTO stats = dashboardService.obtenerEstadisticas(null);
        return ResponseEntity.ok(stats);
    }

    // 2. Ver estadísticas de un usuario en específico
    // Endpoint: GET /logs/user/{usuario}
    @GetMapping("/user/{usuario}")
    public ResponseEntity<?> obtenerLogsDeUsuario(@PathVariable String usuario) {
        // Se llama con el nombre del usuario
        DashLogsDTO stats = dashboardService.obtenerEstadisticas(usuario);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/filter/{usuario}")
    public ResponseEntity<?> obtenerLogUsuario(@PathVariable String usuario){
        List<PredictionLog> logs = dashboardService.obtenerLogsUsuario(usuario);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/filter/fecha/{usuario}")
    public ResponseEntity<?> obtenerLogUsuarioDesde(@PathVariable String usuario, @RequestBody @Valid FechasLimiteDTO fechas){
        List<PredictionLog> logs = dashboardService.obtenerLogsUsuarioYFecha(usuario, fechas.getFechaDesde(), fechas.getFechaHasta());
        return ResponseEntity.ok(logs);
    }


}