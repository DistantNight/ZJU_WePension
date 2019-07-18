package com.zjuwepension.application.service.impl;

import com.zjuwepension.AppPush;
import com.zjuwepension.application.entity.AlertHistory;
import com.zjuwepension.application.entity.AlertTemplate;
import com.zjuwepension.application.entity.UserButton;
import com.zjuwepension.application.repository.AlertTempalteRepository;
import com.zjuwepension.application.service.AlertHistoryService;
import com.zjuwepension.application.service.AlertTemplateService;
import com.zjuwepension.application.service.UserButtonService;
import com.zjuwepension.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlertTemplateServiceImpl implements AlertTemplateService {
    @Autowired
    private AlertTempalteRepository alertTempalteRepository;
    @Autowired
    private AlertHistoryService alertHistoryService;
    @Autowired
    private UserButtonService userButtonService;

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    @Override
    public AlertTemplate saveAlertTemplate(AlertTemplate template){
        return alertTempalteRepository.save(template);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    @Override
    public AlertTemplate updateAlertTemplate(AlertTemplate template){
        return alertTempalteRepository.save(template);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    @Override
    public AlertTemplate findActiveTemplateByButtonId(Long buttonId){
        List<AlertTemplate> list = alertTempalteRepository.findAlertTemplatesByButtonIdAndIsActive(buttonId, true);
        if(null != list && 1 == list.size())
            return list.get(0);
        return null;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    @Override
    public AlertTemplate findTemplateByTemplateId(Long templateId){
        List<AlertTemplate> list = alertTempalteRepository.findAlertTemplatesByAlertTemplateId(templateId);
        if(null != list && 1 == list.size())
            return list.get(0);
        return null;
    }

    @Transactional
    @Override
    public String addAlertHistoryFromTemplate(Long buttonId){
        AlertTemplate template = findActiveTemplateByButtonId(buttonId);
        if (null == template)
            return "订单模板不存在";
        if (alertHistoryService.hasUnConfirmedHistory(template.getAlertTemplateId()))
            return "存在为确定求助";
        AlertHistory history = new AlertHistory();
        history.setAlertTemplateId(template.getAlertTemplateId());
        history.setDate(Tool.getDate());
        history.setIsConfirmed(false);
        UserButton userButton = userButtonService.findUserButtonByButtonId(buttonId);
        if(null == userButton)
            return "未绑定按钮";
        history.setUserId(userButton.getUserId());
        alertHistoryService.saveAlertHistory(history);
        try{
            AppPush.pushMsg("求助按钮！", "求助信息:" + template.getAlertMessage());
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
