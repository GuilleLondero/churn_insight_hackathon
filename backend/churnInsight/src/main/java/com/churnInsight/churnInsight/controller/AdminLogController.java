package com.churnInsight.churnInsight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.churnInsight.churnInsight.domain.dto.DashLogsDTO;
import com.churnInsight.churnInsight.service.DashboardService;

@RestController
@RequestMapping("/logs")
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
}