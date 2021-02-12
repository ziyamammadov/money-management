package com.ziya.moneymanagement.entity;

import com.ziya.moneymanagement.model.enums.AccountType;
import com.ziya.moneymanagement.model.enums.Currency;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountName;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private Currency accountCurrency;

    private String description;

    private double balance;

    private double creditLimit;

    @OneToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "account")
    private List<Transaction> transactions;

    public boolean includeInTotalBalance;

    private boolean enableSchedule;
    @Transient
    private Long countOfTransactions;


}
