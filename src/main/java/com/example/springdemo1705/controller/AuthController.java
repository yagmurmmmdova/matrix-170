package com.example.springdemo1705.controller;

import com.example.springdemo1705.model.AuthRequestDto;
import com.example.springdemo1705.model.AuthResponseDto;
import com.example.springdemo1705.model.RefreshTokenRequestDto;
import com.example.springdemo1705.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody AuthRequestDto request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponseDto refreshToken(@Valid @RequestBody RefreshTokenRequestDto request) {
        return authService.refreshToken(request);
    }
}