package com.ziya.moneymanagement.entity;

import com.ziya.moneymanagement.entity.enums.CategoryType;
import com.ziya.moneymanagement.entity.enums.Currency;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double categoryBalance;

    private String description;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "category")
    private List<Transaction> transactions;
}
