package com.example.springdemo1705.model;

import java.time.LocalDateTime;

public record ExceptionResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {}
