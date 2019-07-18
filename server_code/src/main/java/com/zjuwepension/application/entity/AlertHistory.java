package com.zjuwepension.application.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertHistory {
    @Id
    @GeneratedValue(generator = "alertHistoryId")
    private Long alertHistoryId;
    private Long alertTemplateId;
    private Long userId;
    private String Date;
    private Boolean isConfirmed;
}
