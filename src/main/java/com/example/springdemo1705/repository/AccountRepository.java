package com.example.springdemo1705.repository;

import com.example.springdemo1705.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
