package com.ziya.moneymanagement.controller;

import com.ziya.moneymanagement.exception.*;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MyExceptionHandler {
    Logger logger = Logger.getLogger(this.getClass());

    @ExceptionHandler(GeneralException.class)
    public ExceptionEntity handleGeneralException(GeneralException ex) {
        logger.error(String.format("ERROR - %s, %s", "200", ex.getMessage()));
        return ExceptionEntity.builder()
                .code(200)
                .description(ex.getMessage())
                .build();
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ExceptionEntity handleAccountNotFoundException() {
        logger.error(String.format("ERROR - %s, %s", "404", "Account not found"));
        return ExceptionEntity.builder()
                .code(404)
                .description("Account not found")
                .build();
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ExceptionEntity handleCategoryNotFoundException() {
        logger.error(String.format("ERROR - %s, %s", "404", "Category not found"));
        return ExceptionEntity.builder()
                .code(404)
                .description("Category not found")
                .build();
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ExceptionEntity handleTransactionNotFoundException() {
        logger.error(String.format("ERROR - %s, %s", "404", "Transaction not found"));
        return ExceptionEntity.builder()
                .code(404)
                .description("Transaction not found")
                .build();
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public ExceptionEntity handleUserNotActivatedException(Exception exception) {
        logger.error(String.format("ERROR - %s, %s", "400", "User is not activated"));
        return ExceptionEntity.builder()
                .code(400)
                .description(exception.getMessage())
                .build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ExceptionEntity handleUserNotFoundException() {
        logger.error(String.format("ERROR - %s, %s", "404", "User not found"));
        return ExceptionEntity.builder()
                .code(404)
                .description("User not found")
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ExceptionEntity validationErrorHandler(ConstraintViolationException ex) {
        List<String> errorsList = new ArrayList<>(ex.getConstraintViolations().size());

        ex.getConstraintViolations().forEach(error -> errorsList.add(error.toString()));

        return ExceptionEntity.builder()
                .code(404)
                .description(errorsList.toString())
                .build();
    }

}
