package com.ziya.moneymanagement.controller;


import com.ziya.moneymanagement.config.scheduling.MyTaskScheduler;
import com.ziya.moneymanagement.config.scheduling.ScheduleEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchedulerController {
    private final MyTaskScheduler scheduler;


    public SchedulerController(MyTaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping("/schedule/{amount}/{accountId}/{categoryId}")
    public void setScheduler(@PathVariable Double amount, @PathVariable Long accountId, @PathVariable Long categoryId) {
        scheduler.scheduleRunnableWithCronTrigger(new ScheduleEntity(amount, accountId, categoryId));
    }
}