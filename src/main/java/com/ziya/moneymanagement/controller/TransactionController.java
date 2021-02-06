package com.ziya.moneymanagement.controller;

import com.ziya.moneymanagement.entity.FilterDate;
import com.ziya.moneymanagement.entity.Transaction;
import com.ziya.moneymanagement.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Transaction> getAll() {
        return service.getAll();
    }

    @GetMapping("/getByAccountId/{id}")
    public List<Transaction> getByAccountId(@PathVariable Long id) {
        return service.findByAccountId(id);
    }

    @GetMapping("/{id}")
    public Transaction getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public Transaction save(@RequestBody Transaction transaction) {
        return service.save(transaction);
    }

    @PutMapping
    public Transaction update(@RequestBody Transaction transaction) {
        return service.save(transaction);
    }

    @DeleteMapping("/{id}")
    public Transaction deleteById(@PathVariable Long id) {
        return service.deleteById(id);
    }

    @GetMapping("getAccountsByDate")
    public List<Transaction> getTransactionsByDate(@RequestBody FilterDate filter) {
        return service.getTransactionsByDate(filter.getDate1(), filter.getDate2());
    }

}
