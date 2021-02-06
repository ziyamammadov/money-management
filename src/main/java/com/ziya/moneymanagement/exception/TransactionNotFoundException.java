package com.ziya.moneymanagement.exception;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException() {
        super();
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }
}
