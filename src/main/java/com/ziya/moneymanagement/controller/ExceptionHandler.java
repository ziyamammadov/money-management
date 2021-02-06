package com.ziya.moneymanagement.controller;

import com.ziya.moneymanagement.exception.*;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionHandler {
    Logger logger = Logger.getLogger(this.getClass());

    @org.springframework.web.bind.annotation.ExceptionHandler(GeneralException.class)
    public ExceptionEntity handleGeneralException(GeneralException ex) {
        logger.error(String.format("ERROR - %s, %s", "200", ex.getMessage()));
        return ExceptionEntity.builder()
                .code(200)
                .description(ex.getMessage())
                .build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccountNotFoundException.class)
    public ExceptionEntity handleAccountNotFoundException() {
        logger.error(String.format("ERROR - %s, %s", "404", "Account not found"));
        return ExceptionEntity.builder()
                .code(404)
                .description("Account not found")
                .build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CategoryNotFoundException.class)
    public ExceptionEntity handleCategoryNotFoundException() {
        logger.error(String.format("ERROR - %s, %s", "404", "Category not found"));
        return ExceptionEntity.builder()
                .code(404)
                .description("Category not found")
                .build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TransactionNotFoundException.class)
    public ExceptionEntity handleTransactionNotFoundException() {
        logger.error(String.format("ERROR - %s, %s", "404", "Transaction not found"));
        return ExceptionEntity.builder()
                .code(404)
                .description("Transaction not found")
                .build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionEntity handleHttpMessageNotReadableException() {
        logger.error(String.format("ERROR - %s, %s", "404", "Incorrect json format"));
        return ExceptionEntity.builder()
                .code(404)
                .description("Incorrect json format")
                .build();
    }
}
