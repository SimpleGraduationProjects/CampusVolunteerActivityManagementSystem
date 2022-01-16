package com.collegevol.controller;

import com.collegevol.service.impl.EventServiceImpl;
import com.collegevol.vo.EventCode;
import com.collegevol.vo.ReturnData;
import com.collegevol.vo.StatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/web/")
public class WebController {

    @Resource
    private EventServiceImpl eventService;


    @RequestMapping("getIndexData")
    @ResponseBody
    public ReturnData getIndexData() {
        ReturnData returnData = new ReturnData();
        returnData.setStatusCode(StatusCode.SUCCESS);
        returnData.setMsg("获得数据");
        Map<String, Object> map = new HashMap<>();
        Date startDate = new Date();
        startDate.setHours(0);
        startDate.setMinutes(0);
        startDate.setSeconds(0);
        Date endDate = new Date();
        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate.setSeconds(59);
        Integer today1 = eventService.qryEventVoByStatusBetween(EventCode.UNDER_REVIEW, startDate, endDate);
        Integer today2 = eventService.qryEventVoByStatusBetween(EventCode.IN_PROGRESS, startDate, endDate);
        Integer today3 = eventService.qryEventVoByStatusBetween(EventCode.TIMEOUT, startDate, endDate);
        HashMap<String, Integer> todayMap = new HashMap<>();
        todayMap.put("unchecked", today1);
        todayMap.put("unfinshed", today2 + today3);
        todayMap.put("sum", eventService.qryEventVoBetween(startDate, endDate));
        map.put("today", todayMap);


        Calendar cale = Calendar.getInstance();
        // 获取前月的第一天
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        cale.set(Calendar.HOUR, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.SECOND, 0);
        startDate = cale.getTime();
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        cale.set(Calendar.HOUR, 23);
        cale.set(Calendar.MINUTE, 59);
        cale.set(Calendar.SECOND, 59);
        endDate = cale.getTime();

        HashMap<String, Integer> curMonthMap = new HashMap<>();
        Integer curMonth1 = eventService.qryEventVoByStatusBetween(EventCode.UNDER_REVIEW, startDate, endDate);
        Integer curMonth2 = eventService.qryEventVoByStatusBetween(EventCode.IN_PROGRESS, startDate, endDate);
        Integer curMonth3 = eventService.qryEventVoByStatusBetween(EventCode.TIMEOUT, startDate, endDate);
        curMonthMap.put("unchecked", curMonth1);
        curMonthMap.put("unfinshed", curMonth2 + curMonth3);
        curMonthMap.put("sum", eventService.qryEventVoBetween(startDate, endDate));
        map.put("curMonth", curMonthMap);


        HashMap<String, Integer> sumMap = new HashMap<>();
        Integer sum1 = eventService.qryAllEventVos(EventCode.UNDER_REVIEW).size();
        Integer sum2 = eventService.qryAllEventVos(EventCode.IN_PROGRESS).size();
        Integer sum3 = eventService.qryAllEventVos(EventCode.TIMEOUT).size();
        sumMap.put("unchecked", sum1);
        sumMap.put("unfinshed", sum2 + sum3);
        sumMap.put("sum", eventService.qryAllEventVos("").size());
        map.put("nowSum", sumMap);


        returnData.setData(map);
        return returnData;
    }
}
