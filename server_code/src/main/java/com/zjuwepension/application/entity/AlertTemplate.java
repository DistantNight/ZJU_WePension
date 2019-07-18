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
public class AlertTemplate {
    @Id
    @GeneratedValue(generator = "alertTemplateId")
    private Long alertTemplateId;
    private Long buttonId;
    private String alertPhone;
    private String alertMessage;
    private String date;
    private Boolean isActive;
}
