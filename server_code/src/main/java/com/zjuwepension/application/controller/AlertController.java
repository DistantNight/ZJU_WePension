package com.zjuwepension.application.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zjuwepension.application.entity.AlertHistory;
import com.zjuwepension.application.entity.AlertTemplate;
import com.zjuwepension.application.service.AlertHistoryService;
import com.zjuwepension.application.service.AlertTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alert")
public class AlertController {
    @Autowired
    private AlertTemplateService alertTemplateService;
    @Autowired
    private AlertHistoryService alertHistoryService;

    @PostMapping("/list")
    public String getAlertHistoryList(@RequestBody String body){
        JsonObject jsonDate = new JsonParser().parse(body).getAsJsonObject();
        JsonObject result = new JsonObject();
        if(jsonDate.has("curId")){
            List<AlertHistory> list = alertHistoryService.getAlertHistory(jsonDate.get("curId").getAsLong());
            if (null != list) {
                result.addProperty("IsSuccess",true);
                result.addProperty("ErrorInfo", "");
                result.add("alertHistory", new JsonObject());
                result.get("alertHistory").getAsJsonObject().addProperty("num", new Long(list.size()).toString());
                result.get("alertHistory").getAsJsonObject().add("list", new JsonArray());
                for (int i = 0; i < list.size(); i++){
                    AlertHistory history = list.get(i);
                    AlertTemplate template = alertTemplateService.findTemplateByTemplateId(history.getAlertTemplateId());
                    JsonObject historyElement = new JsonObject();
                    historyElement.addProperty("historyId", history.getAlertHistoryId().toString());
                    historyElement.addProperty("historyDate", history.getDate());
                    historyElement.addProperty("isConfirmed", history.getIsConfirmed());
                    historyElement.addProperty("historyPhone", template.getAlertPhone());
                    historyElement.addProperty("historyMessage", template.getAlertMessage());
                    result.get("alertHistory").getAsJsonObject().get("list").getAsJsonArray().add(historyElement);
                }
            } else {
                result.addProperty("ErrorInfo", "查找失败");
            }
        } else {
            result.addProperty("ErrorInfo","参数不完整");
        }
        if (!result.get("ErrorInfo").getAsString().equals("")){
            result.addProperty("IsSuccess", false);
        }

        return result.toString();
    }


    @PostMapping("/changeState")
    public String changeState(@RequestBody String body){
        JsonObject jsonDate = new JsonParser().parse(body).getAsJsonObject();
        JsonObject result = new JsonObject();
        if (jsonDate.has("curId") && jsonDate.has("historyId")) {
            String info = alertHistoryService.changeHistoryState(jsonDate.get("historyId").getAsLong(), jsonDate.get("curId").getAsLong());
            result.addProperty("ErrorInfo", info);
        } else {
            result.addProperty("ErrorInfo", "参数不完整");
        }
        if (result.get("ErrorInfo").getAsString().equals("")){
            result.addProperty("IsSuccess", true);
        } else {
            result.addProperty("IsSuccess", false);
        }
        return result.toString();
    }
}
