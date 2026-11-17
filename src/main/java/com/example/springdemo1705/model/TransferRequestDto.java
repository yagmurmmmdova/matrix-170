package com.example.springdemo1705.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequestDto(
        @NotNull(message = "Sender account ID is required")
        Long fromAccountId,

        @NotNull(message = "Receiver account ID is required")
        Long toAccountId,

        @NotNull(message = "Amount is required")
        @Positive(message = "Transfer amount must be greater than zero")
        BigDecimal amount
) {
}
