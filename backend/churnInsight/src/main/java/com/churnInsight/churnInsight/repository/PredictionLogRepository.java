package com.churnInsight.churnInsight.repository;

import com.churnInsight.churnInsight.entity.PredictionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionLogRepository extends JpaRepository<PredictionLog, Long> {
   
}

