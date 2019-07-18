package com.zjuwepension.application.repository;

import com.zjuwepension.application.entity.AlertHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertHistoryRepository extends JpaRepository<AlertHistory, Long> {
    List<AlertHistory> findAlertHistoriesByAlertTemplateIdAndIsConfirmed(Long templateId, Boolean isConfirmed);
    List<AlertHistory> findAlertHistoriesByUserId(Long userId);
    List<AlertHistory> findAlertHistoriesByAlertHistoryIdAndUserId(Long historyId, Long userId);
}
