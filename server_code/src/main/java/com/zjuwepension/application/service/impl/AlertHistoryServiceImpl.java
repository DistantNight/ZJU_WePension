package com.zjuwepension.application.service.impl;

import com.zjuwepension.application.entity.AlertHistory;
import com.zjuwepension.application.repository.AlertHistoryRepository;
import com.zjuwepension.application.service.AlertHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlertHistoryServiceImpl implements AlertHistoryService {
    @Autowired
    private AlertHistoryRepository alertHistoryRepository;

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    @Override
    public AlertHistory saveAlertHistory(AlertHistory history){
        return alertHistoryRepository.save(history);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    @Override
    public AlertHistory updateAlertHistory(AlertHistory history){
        return alertHistoryRepository.save(history);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    @Override
    public Boolean hasUnConfirmedHistory(Long templateId){
        List<AlertHistory> list = alertHistoryRepository.findAlertHistoriesByAlertTemplateIdAndIsConfirmed(templateId, false);
        if(null != list && 1 == list.size())
            return true;
        return false;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    @Override
    public List<AlertHistory> getAlertHistory(Long userId){
        return alertHistoryRepository.findAlertHistoriesByUserId(userId);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    @Override
    public String changeHistoryState(Long historyId, Long userId){
        List<AlertHistory> list = alertHistoryRepository.findAlertHistoriesByAlertHistoryIdAndUserId(historyId, userId);
        if(null == list || 1 < list.size())
            return "非法求助";
        AlertHistory history = list.get(0);
        if(history.getIsConfirmed())
            return "错误状态操作";
        history.setIsConfirmed(true);
        updateAlertHistory(history);
        return "";
    }
}
