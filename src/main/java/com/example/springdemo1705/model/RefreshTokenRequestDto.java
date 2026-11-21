package com.example.springdemo1705.model;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDto(
        @NotBlank(message = "Refresh token cannot be blank")
        String refreshToken
) {}