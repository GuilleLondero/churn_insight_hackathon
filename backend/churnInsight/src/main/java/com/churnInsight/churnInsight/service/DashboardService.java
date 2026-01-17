package com.churnInsight.churnInsight.service;

import java.time.Instant;
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
        return repository.getPredictionStats(usuario);
    }

    public List<PredictionLog> obtenerLogsUsuario(String usuario){
        return repository.findByUsuario(usuario);
    }
    
    public List<PredictionLog> obtenerLogsUsuarioYFecha(String usuario, Instant desde, Instant hasta){
        return repository.findByUsuarioAndFechaRango(usuario,desde,hasta);
    }
}