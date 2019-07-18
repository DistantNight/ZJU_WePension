package com.zjuwepension.application.repository;

import com.zjuwepension.application.entity.AlertTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertTempalteRepository extends JpaRepository<AlertTemplate, Long> {
    List<AlertTemplate> findAlertTemplatesByButtonIdAndIsActive(Long buttonId, Boolean isActive);
    List<AlertTemplate> findAlertTemplatesByAlertTemplateId(Long templateId);
}
