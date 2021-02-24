package com.ziya.moneymanagement.config.scheduling;

import com.ziya.moneymanagement.service.AccountServiceImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class MyTaskScheduler {
    private final ThreadPoolTaskScheduler taskScheduler;
    private final AccountServiceImpl service;
    private final CronTrigger cronTrigger;

    public MyTaskScheduler(CronTrigger cronTrigger, ThreadPoolTaskScheduler taskScheduler, AccountServiceImpl service) {
        this.cronTrigger = cronTrigger;
        this.taskScheduler = taskScheduler;
        this.service = service;
    }

    public void scheduleRunnableWithCronTrigger(ScheduleEntity scheduleEntity) {
        scheduleEntity.setEnableSchedule(service.getOne(scheduleEntity.getAccountId()).isEnableSchedule());
        if(scheduleEntity.isEnableSchedule()){
            taskScheduler.schedule(new MyScheduledTask(scheduleEntity, service), cronTrigger);
        }else {
            taskScheduler.shutdown();
        }
    }
}
