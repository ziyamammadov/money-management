package com.ziya.moneymanagement.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Authority {
    @Id
    private String name;
}
