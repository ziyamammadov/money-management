package com.ziya.moneymanagement.controller;

import com.ziya.moneymanagement.entity.Account;
import com.ziya.moneymanagement.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;

    @GetMapping
    public List<Account> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Account getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account save(@RequestBody Account account) {
        return service.save(account);
    }

    @PutMapping
    public Account update(@RequestBody Account account) {
        return service.update(account);
    }

    @DeleteMapping("/{id}")
    public Account deleteById(@PathVariable Long id) {
        return service.deleteById(id);
    }

    @PostMapping("/getMoneyFromAccount/{amount}/{accountId}/{categoryId}")
    public void getMoneyFromAccount(@PathVariable Double amount, @PathVariable Long accountId, @PathVariable Long categoryId) {
        service.removeMoneyFromAccount(amount, accountId, categoryId);
    }

    @PostMapping("/addMoneyToAccount/{amount}/{accountId}/{categoryId}")
    public void addMoneyToAccount(@PathVariable Long accountId, @PathVariable Double amount, @PathVariable Long categoryId) {
        service.addMoneyToAccount(amount, accountId, categoryId);
    }

    @PostMapping("/transferMoney/{amount}/{formAccountId}/{toAccountId}")
    public void addMoneyToAccount(@PathVariable Double amount, @PathVariable Long formAccountId, @PathVariable Long toAccountId) {
        service.transferMoney(amount, formAccountId, toAccountId);
    }
}
