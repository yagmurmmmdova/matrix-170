package com.example.springdemo1705.controller;

import com.example.springdemo1705.exception.AccountNotFoundException;
import com.example.springdemo1705.exception.InsufficientBalanceException;
import com.example.springdemo1705.exception.UserNotFoundException;
import com.example.springdemo1705.exception.UsernameAlreadyExistsException;
import com.example.springdemo1705.model.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ExceptionResponse buildError(HttpStatus status, String message) {
        return new ExceptionResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleException(AccountNotFoundException e) {
        log.error("Account not found error: {}", e.getMessage());
        return buildError(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleException(InsufficientBalanceException e) {
        log.error("Insufficient balance error: {}", e.getMessage());
        return buildError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleException(IllegalArgumentException e) {
        log.warn("Invalid argument error: {}", e.getMessage());
        return buildError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IllegalTransactionStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleException(IllegalTransactionStateException e) {
        log.error("Transaction propagation error (MANDATORY): {}", e.getMessage(), e);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleException(MethodArgumentNotValidException e) {
        log.error("Validation error: {}", e.getBindingResult().getFieldErrors());
        String errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(i -> i.getField() + ": " + i.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return buildError(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleException(RuntimeException e) {
        log.error("Unexpected runtime error occurred: {}", e.getMessage(), e);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleException(UserNotFoundException e) {
        log.error("User not found error: {}", e.getMessage());
        return buildError(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT) //409
    public ExceptionResponse handleException(UsernameAlreadyExistsException e) {
        log.warn("Username already exists error: {}", e.getMessage());
        return buildError(HttpStatus.CONFLICT, e.getMessage());
    }
}
