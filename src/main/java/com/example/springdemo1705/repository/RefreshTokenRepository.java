package com.example.springdemo1705.repository;

import com.example.springdemo1705.entity.RefreshTokenEntity;
import com.example.springdemo1705.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    Optional<RefreshTokenEntity> findByUserEntity(UserEntity userEntity);
}