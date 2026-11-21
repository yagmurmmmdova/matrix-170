package com.example.springdemo1705.model;

public record AuthResponseDto(
        String accessToken,
        String refreshToken
) {}