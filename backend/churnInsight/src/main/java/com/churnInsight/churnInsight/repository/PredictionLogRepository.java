package com.churnInsight.churnInsight.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.churnInsight.churnInsight.domain.dto.DashLogsDTO;
import com.churnInsight.churnInsight.entity.PredictionLog;

@Repository
public interface PredictionLogRepository extends JpaRepository<PredictionLog, Long> {
    
    // Específica por usuario
    List<PredictionLog> findByUsuario(String usuario);

    @Query("""
        SELECT new com.churnInsight.churnInsight.domain.dto.DashLogsDTO(
            COUNT(p),
            MIN(p.timestamp),
            AVG(p.probabilidadChurn),
            MIN(p.probabilidadChurn),
            MAX(p.probabilidadChurn),

            SUM(CASE WHEN p.prediccion = 'cancelara' THEN 1 ELSE 0 END),
            SUM(CASE WHEN p.prediccion = 'no_cancelara' THEN 1 ELSE 0 END),
            SUM(CASE WHEN p.status = 'ERROR' THEN 1 ELSE 0 END),

            SUM(CASE WHEN p.probabilidadChurn >= 0.7 THEN 1 ELSE 0 END),
            SUM(CASE WHEN p.probabilidadChurn >= 0.4 AND p.probabilidadChurn < 0.7 THEN 1 ELSE 0 END),
            SUM(CASE WHEN p.probabilidadChurn < 0.4 THEN 1 ELSE 0 END)
        )
        FROM PredictionLog p
        WHERE (:usuario IS NULL OR p.usuario = :usuario)
    """)
    DashLogsDTO getPredictionStats(@Param("usuario") String usuario);
}