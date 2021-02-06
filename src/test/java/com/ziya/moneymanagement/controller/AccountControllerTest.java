package com.ziya.moneymanagement.controller;

import com.ziya.moneymanagement.entity.Account;
import com.ziya.moneymanagement.entity.Category;
import com.ziya.moneymanagement.entity.Transaction;
import com.ziya.moneymanagement.entity.enums.AccountType;
import com.ziya.moneymanagement.entity.enums.CategoryType;
import com.ziya.moneymanagement.entity.enums.Currency;
import com.ziya.moneymanagement.exception.AccountNotFoundException;
import com.ziya.moneymanagement.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {
    private Account account;
    private List<Account> accountList;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService service;

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

        Transaction transaction = new Transaction();
        transaction.setCategory(category);
        transaction.setTransactionAmount(1000);
        transaction.setAccount(account);
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        account.setTransactions(transactions);

        Account account1 = account = new Account();
        account1.setAccountName("TezPay");
        account1.setAccountType(AccountType.REGULAR);
        account1.setAccountCurrency(Currency.AZN);
        account1.setBalance(3000);
        account1.setCreditLimit(2000);
        account1.setDescription("Initial check");
        account1.setIncludeInTotalBalance(true);
        accountList = new ArrayList<>();
        accountList.add(account1);
        accountList.add(account);
    }

    @Test
    void shouldFetchAllUsers() throws Exception {

        given(service.getAll()).willReturn(accountList);

        this.mockMvc.perform(get("/account"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(accountList.size())));
    }

    @Test
    void shouldFetchOneUserById() throws Exception {
        final Long accountId = 1L;
        account.setId(accountId);

        given(service.getOne(accountId)).willReturn(account);

        this.mockMvc.perform(get("/account/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountName", is(account.getAccountName())))
                .andExpect(jsonPath("$.accountType", is(account.getAccountType().toString())))
                .andExpect(jsonPath("$.accountCurrency", is(account.getAccountCurrency().toString())))
                .andExpect(jsonPath("$.description", is(account.getDescription())))
                .andExpect(jsonPath("$.creditLimit", is(account.getCreditLimit())))
                .andExpect(jsonPath("$.transactions", is(account.getTransactions())))
                .andExpect(jsonPath("$.balance", is(account.getBalance())));
    }

    @Test
    void shouldReturnExceptionWhenFindUserById() throws Exception {
        final Long accountId = 1L;
        given(service.getOne(accountId)).willThrow(new AccountNotFoundException());

        this.mockMvc.perform(get("/account/{id}", accountId))
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.description", is("Account not found")));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        Long accountId = 1L;
        account.setId(accountId);
        given(service.deleteById(accountId)).willReturn(account);

        this.mockMvc.perform(delete("/account/{id}", account.getId()))
                .andExpect(status().isOk());

    }
}