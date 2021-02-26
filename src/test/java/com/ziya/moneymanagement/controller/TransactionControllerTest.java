package com.ziya.moneymanagement.controller;

import com.ziya.moneymanagement.entity.Account;
import com.ziya.moneymanagement.entity.Category;
import com.ziya.moneymanagement.entity.Transaction;
import com.ziya.moneymanagement.exception.TransactionNotFoundException;
import com.ziya.moneymanagement.model.enums.AccountType;
import com.ziya.moneymanagement.model.enums.CategoryType;
import com.ziya.moneymanagement.model.enums.Currency;
import com.ziya.moneymanagement.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = TransactionController.class)
class TransactionControllerTest {
    private Transaction transaction;
    private List<Transaction> transactionList;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;

    @BeforeEach
    void setUp() {
        Account account = new Account();
        account.setId(1L);
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
        transactionList = new ArrayList<>();
        transactionList.add(transaction);
        account.setTransactions(transactions);
    }

    @Test
    void shouldFetchAllUsers() throws Exception {

        given(service.getAll()).willReturn(transactionList);

        this.mockMvc.perform(get("/transaction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(transactionList.size())));
    }

    @Test
    void shouldFetchOneUserById() throws Exception {
        final Long categoryId = 1L;
        transaction.setId(categoryId);

        given(service.getOne(categoryId)).willReturn(transaction);
        this.mockMvc.perform(get("/transaction/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category", is(transaction.getCategory())))
                .andExpect(jsonPath("$.transactionAmount", is(transaction.getTransactionAmount())));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        Long categoryId = 1L;
        transaction.setId(categoryId);
        given(service.deleteById(categoryId)).willReturn(transaction);

        this.mockMvc.perform(delete("/transaction/{id}", transaction.getId()))
                .andExpect(status().isOk());

    }
}