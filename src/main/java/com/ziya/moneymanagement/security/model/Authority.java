package com.ziya.moneymanagement.security.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Authority {
    @Id
    private String name;
}
