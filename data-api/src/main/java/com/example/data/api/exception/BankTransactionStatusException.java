package com.example.data.api.exception;

public class BankTransactionStatusException extends RuntimeException {
    public BankTransactionStatusException(String message) {
        super(message);
    }
}
