package com.ziya.moneymanagement.service;

import com.ziya.moneymanagement.entity.Account;
import com.ziya.moneymanagement.entity.Category;
import com.ziya.moneymanagement.entity.Transaction;
import com.ziya.moneymanagement.model.enums.AccountType;
import com.ziya.moneymanagement.model.enums.CategoryType;
import com.ziya.moneymanagement.model.enums.Currency;
import com.ziya.moneymanagement.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository repository;
    @InjectMocks
    private AccountService service;
    private Account account;

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
    }

    @Test
    void shouldSavedUserSuccessFully() {
        given(repository.save(account)).willAnswer(invocation -> invocation.getArgument(0));

        Account savedAccount = service.save(this.account);

        assertThat(savedAccount).isNotNull();

        verify(repository).save(any(Account.class));
    }

    @Test
    void updateUser() {
        account.setId(1L);

        given(repository.findById(1L)).willReturn(Optional.of(account));
        given(repository.save(account)).willReturn(account);

        Account expected = service.update(this.account);

        assertThat(expected).isNotNull();

        verify(repository).save(any(Account.class));
    }

    @Test
    void shouldReturnFindAll() {
        List<Account> data = new ArrayList<>();
        data.add(account);

        given(repository.findAll()).willReturn(data);

        List<Account> expected = service.getAll();
        account.setId(1L);
        expected.add(account);

        assertEquals(expected, data);
    }

    @Test
    void findUserById() {
        final Long id = 1L;
        account.setId(id);
        given(repository.findById(id)).willReturn(Optional.of(account));

        Account expected = service.getOne(id);

        assertThat(expected).isNotNull();
    }

    @Test
    void shouldBeDelete() {
        final Long accountId = 1L;
        given(repository.findById(accountId)).willReturn(Optional.of(account));

        service.deleteById(accountId);
        service.deleteById(accountId);

        verify(repository, times(2)).deleteById(accountId);
    }
}