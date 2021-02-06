package com.ziya.moneymanagement.scheduling;

import com.ziya.moneymanagement.service.AccountService;

public class MyScheduledTask implements Runnable {
    private final ScheduleEntity schedule;
    private final AccountService service;

    public MyScheduledTask(ScheduleEntity scheduleEntity, AccountService service) {
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
