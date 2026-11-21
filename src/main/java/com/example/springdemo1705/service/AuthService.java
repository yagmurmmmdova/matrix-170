package com.example.springdemo1705.service;

import com.example.springdemo1705.entity.RefreshTokenEntity;
import com.example.springdemo1705.model.AuthRequestDto;
import com.example.springdemo1705.model.AuthResponseDto;
import com.example.springdemo1705.model.RefreshTokenRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthResponseDto login(AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        String accessToken = jwtService.generateAccessToken(userDetails);
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(request.username());

        return new AuthResponseDto(accessToken, refreshToken.getToken());
    }

    public AuthResponseDto refreshToken(RefreshTokenRequestDto request) {
        RefreshTokenEntity refreshToken = refreshTokenService.findByToken(request.refreshToken());
        refreshTokenService.verifyExpiration(refreshToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(
                refreshToken.getUserEntity().getUsername()
        );
        String newAccessToken = jwtService.generateAccessToken(userDetails);

        return new AuthResponseDto(newAccessToken, refreshToken.getToken());
    }
}