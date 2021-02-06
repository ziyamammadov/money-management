package com.ziya.moneymanagement.scheduling;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScheduleEntity {
    private double amount;
    private Long accountId;
    private Long categoryId;
    private boolean enableSchedule;

    public ScheduleEntity(double amount, Long accountId, Long categoryId) {
        this.amount = amount;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }
}
