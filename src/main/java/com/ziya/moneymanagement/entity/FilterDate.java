package com.ziya.moneymanagement.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FilterDate {
    private LocalDateTime date1;
    private LocalDateTime date2;
}
