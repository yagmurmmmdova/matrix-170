package com.example.springdemo1705.model;

import java.math.BigDecimal;

public record AccountDto(
        Long id,
        String ownerName,
        BigDecimal balance
) {}
