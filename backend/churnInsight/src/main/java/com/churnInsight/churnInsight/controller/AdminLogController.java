package com.churnInsight.churnInsight.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin") // Rutas de administración
public class AdminLogController {

    @GetMapping("/logs")
    public ResponseEntity<List<String>> getSystemLogs() {

        try {

            // Busca el archivo generado
            Path logPath = Paths.get("logs/churn-insight.log");

            if (!Files.exists(logPath)) {
                return ResponseEntity.ok(Collections.singletonList("El sistema está encendido, pero aún no hay registros de telemetría hoy."));
            }

            // Lee el archivo y se envia como lista
            List<String> lines = Files.readAllLines(logPath);
            return ResponseEntity.ok(lines);

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(Collections.singletonList("Error crítico leyendo logs: " + e.getMessage()));
        }
    }
}
