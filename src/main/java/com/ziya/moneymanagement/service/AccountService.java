package com.ziya.moneymanagement.service;

import com.ziya.moneymanagement.entity.Account;
import com.ziya.moneymanagement.entity.Category;
import com.ziya.moneymanagement.entity.Transaction;
import com.ziya.moneymanagement.exception.AccountNotFoundException;
import com.ziya.moneymanagement.exception.CategoryNotFoundException;
import com.ziya.moneymanagement.repository.AccountRepository;
import com.ziya.moneymanagement.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final CategoryRepository categoryRepository;

    @Cacheable(value = "allAccounts", unless = "#result == null")
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Cacheable(value = "allAccounts", unless = "#result == null", key = "#id")
    public Account getOne(Long id) {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    @CacheEvict(value = "allAccounts", key = "#result.id")
    public Account update(Account account) {
        Long id = account.getId();
        return accountRepository.findById(id)
                .map(acc -> accountRepository.save(account))
                .orElseThrow(AccountNotFoundException::new);
    }

    @CachePut(value = "allAccounts", key = "#account.id")
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @CacheEvict(value = "allAccounts", key = "#id")
    public Account deleteById(Long id) {
        return accountRepository.findById(id)
                .map(acc -> {
                    accountRepository.deleteById(id);
                    return acc;
                })
                .orElseThrow(AccountNotFoundException::new);
    }

    @Transactional
    public void removeMoneyFromAccount(double amount, Long fromAccountId, Long toCategoryId) {
        Category category = categoryRepository.findById(toCategoryId).orElseThrow(CategoryNotFoundException::new);
        Account account = accountRepository.findById(fromAccountId).orElseThrow(AccountNotFoundException::new);

        account.setBalance(account.getBalance() - amount);
        category.setCategoryBalance(category.getCategoryBalance() + amount);
        accountRepository.save(account);
        categoryRepository.save(category);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setTransactionAmount(amount);
        transactionService.save(transaction);
    }

    @Transactional
    public void addMoneyToAccount(double amount, Long fromCategoryId, Long toAccountId) {
        Category category = categoryRepository.findById(fromCategoryId).orElseThrow(CategoryNotFoundException::new);
        Account account = accountRepository.findById(toAccountId).orElseThrow(AccountNotFoundException::new);

        category.setCategoryBalance(category.getCategoryBalance() + amount);
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        categoryRepository.save(category);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setTransactionAmount(amount);
        transactionService.save(transaction);
    }

    @Transactional
    public void transferMoney(double amount, Long fromAccountId, Long toAccountId) {
        Account sourceAccount = accountRepository.findById(fromAccountId).orElseThrow(AccountNotFoundException::new);
        Account destAccount = accountRepository.findById(toAccountId).orElseThrow(AccountNotFoundException::new);

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destAccount.setBalance(destAccount.getBalance() + amount);
        accountRepository.save(sourceAccount);
        accountRepository.save(destAccount);
        Transaction transaction = new Transaction();
        transaction.setAccount(sourceAccount);
        transaction.setDestAccount(destAccount);
        transaction.setTransactionAmount(amount);
        transactionService.save(transaction);

    }
}
