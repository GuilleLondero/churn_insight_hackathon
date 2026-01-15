package com.churnInsight.churnInsight.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.churnInsight.churnInsight.entity.PredictionLog;

@Repository
public interface PredictionLogRepository extends JpaRepository<PredictionLog, Long> {
    
    // Específica por usuario
    List<PredictionLog> findByUsuario(String usuario);
}