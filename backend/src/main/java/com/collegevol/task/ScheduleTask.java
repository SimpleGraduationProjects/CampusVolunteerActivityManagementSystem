package com.collegevol.task;

import com.collegevol.service.impl.EventServiceImpl;
import com.collegevol.service.impl.UserInfoServiceImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@EnableScheduling
public class ScheduleTask {

    @Resource
    private EventServiceImpl eventService;

    @Resource
    private UserInfoServiceImpl userInfoService;
    /**
     * 将过期的活动设置为已结束状态定时任务每天12点跑一次
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void configureTasks() {
        eventService.updateTask();
    }

    /**
     * 每月1号恢复分数
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void configureTask2() {
        userInfoService.updateBaseScoreTask();
    }
}
