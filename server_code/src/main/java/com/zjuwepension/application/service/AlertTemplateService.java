package com.zjuwepension.application.service;

import com.zjuwepension.application.entity.AlertTemplate;

public interface AlertTemplateService {
    AlertTemplate saveAlertTemplate(AlertTemplate template);
    AlertTemplate updateAlertTemplate(AlertTemplate template);
    AlertTemplate findActiveTemplateByButtonId(Long buttonId);
    AlertTemplate findTemplateByTemplateId(Long templateId);
    String addAlertHistoryFromTemplate(Long buttonId);
}
