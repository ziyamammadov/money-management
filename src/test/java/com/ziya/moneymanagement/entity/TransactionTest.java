package com.ziya.moneymanagement.entity;

import com.ziya.moneymanagement.model.enums.AccountType;
import com.ziya.moneymanagement.model.enums.CategoryType;
import com.ziya.moneymanagement.model.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionTest {
    private Category category;
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

        category = new Category();
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
                () -> assertEquals(category, transaction.getCategory(), "Category did not match"),
                () -> assertEquals(1000, transaction.getTransactionAmount(), "Transaction amount did not match"),
                () -> assertEquals(account, transaction.getAccount(), "Account did not match"));
    }
}