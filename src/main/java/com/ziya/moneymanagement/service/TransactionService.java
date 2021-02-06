package com.ziya.moneymanagement.service;

import com.ziya.moneymanagement.entity.Transaction;
import com.ziya.moneymanagement.exception.TransactionNotFoundException;
import com.ziya.moneymanagement.repository.TransactionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }
    @Cacheable(value = "allTransactions", unless = "#result == null")
    public List<Transaction> getAll() {
        return repository.findAll();
    }

    @Cacheable(value = "allTransactions", unless = "#result == null", key = "#id")
    public Transaction getOne(Long id) {
        return repository.findById(id).orElseThrow(TransactionNotFoundException::new);
    }

    @CachePut(value = "allTransactions", key = "#transaction.id")
    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    @CacheEvict(value = "allTransactions", key = "#result.id")
    public Transaction update(Transaction transaction) {
        Long id = transaction.getId();
        return repository.findById(id)
                .map(tran -> repository.save(transaction))
                .orElseThrow(TransactionNotFoundException::new);
    }

    @CacheEvict(value = "allTransactions", key = "#id")
    public Transaction deleteById(Long id) {
        return repository.findById(id)
                .map(tran -> {
                    repository.deleteById(id);
                    return tran;
                })
                .orElseThrow(TransactionNotFoundException::new);
    }

    public List<Transaction> findByAccountId(Long id) {
        return repository.findByAccount_Id(id);
    }


    public List<Transaction> getTransactionsByDate(LocalDateTime date1, LocalDateTime date2) {
        return repository.getTransactionsByCreatedDateTimeBetween(date1, date2);
    }

}
