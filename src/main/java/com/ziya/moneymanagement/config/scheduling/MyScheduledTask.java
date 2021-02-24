package com.ziya.moneymanagement.config.scheduling;

import com.ziya.moneymanagement.service.AccountServiceImpl;

public class MyScheduledTask implements Runnable {
    private final ScheduleEntity schedule;
    private final AccountServiceImpl service;

    public MyScheduledTask(ScheduleEntity scheduleEntity, AccountServiceImpl service) {
        this.schedule = scheduleEntity;
        this.service = service;
    }

    @Override
    public void run() {
        if (schedule.isEnableSchedule()) {
            System.out.println(schedule);
            service.addMoneyToAccount(schedule.getAmount(), schedule.getCategoryId(), schedule.getAccountId());
        }
    }
}
