package com.ziya.moneymanagement.entity;

import com.ziya.moneymanagement.model.enums.AccountType;
import com.ziya.moneymanagement.model.enums.CategoryType;
import com.ziya.moneymanagement.model.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {
    private Account account;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setAccountName("TezPay");
        account.setAccountType(AccountType.REGULAR);
        account.setAccountCurrency(Currency.AZN);
        account.setBalance(5000);
        account.setCreditLimit(2000);
        account.setDescription("Initial check");
        account.setIncludeInTotalBalance(true);

        Category category = new Category();
        category.setCurrency(Currency.AZN);
        category.setDescription("Communal");
        category.setType(CategoryType.EXPENSE);
        category.setName("AzerIshiq");

        transaction = new Transaction();
        transaction.setCategory(category);
        transaction.setTransactionAmount(1000);
        transaction.setAccount(account);
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        account.setTransactions(transactions);
    }

    @Test
    void dependentAssertions() {

        assertAll("Properties Test",
                () -> assertEquals("TezPay", account.getAccountName(), "Account name did not match"),
                () -> assertEquals(AccountType.REGULAR, account.getAccountType(), "Account type did not match"),
                () -> assertEquals(Currency.AZN, account.getAccountCurrency(), "Account currency did not match"),
                () -> assertEquals(5000, account.getBalance(), "Account balance did not match"),
                () -> assertEquals(2000, account.getCreditLimit(), "Credit limit did not match"),
                () -> assertEquals("Initial check", account.getDescription(), "Account description did not match"),
                () -> assertEquals(Collections.singletonList(transaction).size(), account.getTransactions().size(), "Transaction size did not match"),
                () -> assertEquals("Initial check", account.getDescription(), "Account description did not match"));
    }
}