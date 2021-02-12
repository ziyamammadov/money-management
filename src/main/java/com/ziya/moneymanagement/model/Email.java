package com.ziya.moneymanagement.model;

import lombok.Data;

@Data
public class Email {
    private String to;
    private String subject;
    private String text;
}
