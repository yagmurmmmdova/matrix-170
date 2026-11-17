package com.example.springdemo1705.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(Long accountId) {
        super("Insufficient balance in account ID: " + accountId);
    }
}
