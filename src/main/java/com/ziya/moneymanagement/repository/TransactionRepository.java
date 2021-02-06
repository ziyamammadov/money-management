package com.ziya.moneymanagement.repository;

import com.ziya.moneymanagement.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount_Id(Long Id);

    List<Transaction> getTransactionsByCreatedDateTimeBetween(LocalDateTime date1, LocalDateTime date2);
}
