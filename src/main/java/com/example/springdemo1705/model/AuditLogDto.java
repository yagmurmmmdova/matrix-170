package com.example.springdemo1705.model;

import java.time.LocalDateTime;

public record AuditLogDto(
        Long id,
        String message,
        LocalDateTime createdAt
) {}
