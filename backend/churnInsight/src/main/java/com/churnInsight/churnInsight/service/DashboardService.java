package com.churnInsight.churnInsight.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.churnInsight.churnInsight.domain.dto.DashLogsDTO;
import com.churnInsight.churnInsight.repository.PredictionLogRepository;

@Service
public class DashboardService {

    @Autowired
    private PredictionLogRepository repository;

    public DashLogsDTO obtenerEstadisticas(String usuario) {
        return repository.getPredictionStats(usuario);
    }
}