package com.example.springdemo1705.service;

import com.example.springdemo1705.entity.Account;
import com.example.springdemo1705.exception.AccountNotFoundException;
import com.example.springdemo1705.exception.InsufficientBalanceException;
import com.example.springdemo1705.model.AccountDto;
import com.example.springdemo1705.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(a -> new AccountDto(
                        a.getId(),
                        a.getOwnerName(),
                        a.getBalance()))
                .toList();
    }

    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        return new AccountDto(
                account.getId(),
                account.getOwnerName(),
                account.getBalance()
        );
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void debit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(accountId);
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void credit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }
}
