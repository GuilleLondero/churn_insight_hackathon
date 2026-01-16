package com.churnInsight.churnInsight.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.churnInsight.churnInsight.domain.dto.DashLogsDTO;
import com.churnInsight.churnInsight.entity.PredictionLog;
import com.churnInsight.churnInsight.repository.PredictionLogRepository;

@Service
public class DashboardService {

    @Autowired
    private PredictionLogRepository repository;

    public DashLogsDTO obtenerEstadisticas(String usuario) {
        //List<PredictionLog> logs;
//
        //if (usuario == null || usuario.isEmpty()) {
        //    logs = repository.findAll(); // Admin ve todo 
        //} else {
        //    logs = repository.findByUsuario(usuario); // Usuario ve solo lo suyo 
        //}
//
        //return calcularMetricas(logs);
        return repository.getPredictionStats(usuario);
    }

   /*  private DashLogsDTO calcularMetricas(List<PredictionLog> logs) {
        DashLogsDTO stats = new DashLogsDTO();

        // 1. Total de Predicciones
        stats.setTotalPredicciones(logs.size());

        if (logs.isEmpty()) {
            // Se inicia en ceros si no hay datos 
            stats.setFechaPrimerPrediccion(null);
            stats.setProbabilidadChurnPromedio(0f);
            stats.setValorMinimoProbabilidad(0f);
            stats.setValorMaximoProbabilidad(0f);
            stats.setTotalCancelara(0);
            stats.setTotalNoCancelara(0);
            stats.setTotalErrorPrediccion(0);
            stats.setTotalRiesgoAlto(0);
            stats.setTotalRiesgoMedio(0);
            stats.setTotalRiesgoBajo(0);
            return stats;
        }

        // 2. Fecha de la primera predicción 
        // Convertimos de Instant a Date 
        Instant instantMasAntiguo = logs.stream()
                .map(PredictionLog::getTimestamp)
                .min(Instant::compareTo)
                .orElse(null);
        
        if (instantMasAntiguo != null) {
            stats.setFechaPrimerPrediccion(Date.from(instantMasAntiguo));
        }

        // 3. Filtramos los datos sin errores 
        List<PredictionLog> logsValidos = logs.stream()
                .filter(l -> !"ERROR".equalsIgnoreCase(l.getStatus())) 
                .toList();

        // 4. Estadísticas de Probabilidad 
        double promedio = logsValidos.stream()
                .mapToDouble(PredictionLog::getProbabilidadChurn)
                .average()
                .orElse(0.0);
        stats.setProbabilidadChurnPromedio((float) promedio);

        double min = logsValidos.stream()
                .mapToDouble(PredictionLog::getProbabilidadChurn)
                .min()
                .orElse(0.0);
        stats.setValorMinimoProbabilidad((float) min);

        double max = logsValidos.stream()
                .mapToDouble(PredictionLog::getProbabilidadChurn)
                .max()
                .orElse(0.0);
        stats.setValorMaximoProbabilidad((float) max);

        // 5. Conteo por decisión
        long cancelara = logsValidos.stream()
                .filter(l -> "Va a cancelar".equalsIgnoreCase(l.getPrediccion()))
                .count();
        stats.setTotalCancelara((int) cancelara);
        stats.setTotalNoCancelara(logsValidos.size() - (int) cancelara);

        // 6. Conteo de Errores
        long errores = logs.stream()
                .filter(l -> "ERROR".equalsIgnoreCase(l.getStatus()))
                .count();
        stats.setTotalErrorPrediccion((int) errores);

        // 7. Clasificación de Riesgos
        // Alto > 0.7 | Bajo < 0.3 | Medio el resto
        long riesgoAlto = logsValidos.stream().filter(l -> l.getProbabilidadChurn() > 0.7).count();
        long riesgoBajo = logsValidos.stream().filter(l -> l.getProbabilidadChurn() < 0.3).count();
        long riesgoMedio = logsValidos.size() - riesgoAlto - riesgoBajo;

        stats.setTotalRiesgoAlto((int) riesgoAlto);
        stats.setTotalRiesgoBajo((int) riesgoBajo);
        stats.setTotalRiesgoMedio((int) riesgoMedio);

        return stats;
    } */
}