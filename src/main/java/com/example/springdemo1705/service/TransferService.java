package com.example.springdemo1705.service;

import com.example.springdemo1705.model.TransferRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountService accountService;
    private final AuditLogService auditLogService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void transfer(TransferRequestDto requestDto) {
        if (requestDto.fromAccountId().equals(requestDto.toAccountId())) {
            throw new IllegalArgumentException("Sender and receiver accounts cannot be the same");
        }

        auditLogService.saveLog("Transfer attempted");

        accountService.debit(requestDto.fromAccountId(), requestDto.amount());
        accountService.credit(requestDto.toAccountId(), requestDto.amount());

//        throw new RuntimeException("Test Exception - Simulated failure after audit logging");

    }

}
