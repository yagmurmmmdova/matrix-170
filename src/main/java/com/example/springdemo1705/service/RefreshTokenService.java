package com.example.springdemo1705.service;

import com.example.springdemo1705.entity.RefreshTokenEntity;
import com.example.springdemo1705.entity.UserEntity;
import com.example.springdemo1705.exception.InvalidTokenException;
import com.example.springdemo1705.exception.UserNotFoundException;
import com.example.springdemo1705.repository.RefreshTokenRepository;
import com.example.springdemo1705.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public RefreshTokenEntity createRefreshToken(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        RefreshTokenEntity refreshToken = refreshTokenRepository.findByUserEntity(user)
                .orElse(RefreshTokenEntity.builder().userEntity(user).build());

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new InvalidTokenException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    public RefreshTokenEntity findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException("Refresh token is not in database!"));
    }
}