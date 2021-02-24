package com.ziya.moneymanagement.service;

public interface AccountService {
    void addMoneyToAccount(double amount, Long fromCategoryId, Long toAccountId);

    void transferMoney(double amount, Long fromAccountId, Long toAccountId);

}
