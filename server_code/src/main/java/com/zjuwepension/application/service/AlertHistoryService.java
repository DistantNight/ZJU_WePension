package com.zjuwepension.application.service;

import com.zjuwepension.application.entity.AlertHistory;

import java.util.List;

public interface AlertHistoryService {
    AlertHistory saveAlertHistory(AlertHistory history);
    AlertHistory updateAlertHistory(AlertHistory history);
    Boolean hasUnConfirmedHistory(Long templateId);
    List<AlertHistory> getAlertHistory(Long userId);
    String changeHistoryState(Long historyId, Long userId);
}
