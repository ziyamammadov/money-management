package com.ziya.moneymanagement.controller;

import com.ziya.moneymanagement.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(GeneralException.class)
    public ExceptionEntity handleGeneralException(GeneralException ex) {
        return ExceptionEntity.builder()
                .code(200)
                .description(ex.getMessage())
                .build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccountNotFoundException.class)
    public ExceptionEntity handleAccountNotFoundException() {
        return ExceptionEntity.builder()
                .code(404)
                .description("Account not found")
                .build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CategoryNotFoundException.class)
    public ExceptionEntity handleCategoryNotFoundException() {
        return ExceptionEntity.builder()
                .code(404)
                .description("Category not found")
                .build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TransactionNotFoundException.class)
    public ExceptionEntity handleTransactionNotFoundException() {
        return ExceptionEntity.builder()
                .code(404)
                .description("Transaction not found")
                .build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionEntity handleHttpMessageNotReadableException() {
        return ExceptionEntity.builder()
                .code(404)
                .description("Incorrect json format")
                .build();
    }
}
