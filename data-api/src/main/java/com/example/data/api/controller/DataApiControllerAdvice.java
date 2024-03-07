package com.example.data.api.controller;

import com.example.data.api.exception.BankTransactionStatusException;
import com.example.data.api.exception.DebitException;
import jakarta.persistence.EntityNotFoundException;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DataApiControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidException(MethodArgumentNotValidException exception) {
        String exceptionMessage = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + " - " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exception) {
        return getStringResponseEntity(exception);
    }

    @ExceptionHandler(BankTransactionStatusException.class)
    public ResponseEntity<String> handleBankTransactionStatusException(
            BankTransactionStatusException exception
    ) {
        return getStringResponseEntity(exception);
    }

    @ExceptionHandler(DebitException.class)
    public ResponseEntity<String> handleDebitException(DebitException exception) {
        return getStringResponseEntity(exception);
    }

    private ResponseEntity<String> getStringResponseEntity(RuntimeException exception) {
        String exceptionMessage = exception.getMessage();
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }
}
