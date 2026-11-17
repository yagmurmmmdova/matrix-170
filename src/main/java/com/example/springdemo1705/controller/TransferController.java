package com.example.springdemo1705.controller;

import com.example.springdemo1705.model.TransferRequestDto;
import com.example.springdemo1705.service.AccountService;
import com.example.springdemo1705.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfers")
public class TransferController {

    private final TransferService transferService;
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void transfer(@Valid @RequestBody TransferRequestDto requestDto) {
        transferService.transfer(requestDto);
    }

//    @PostMapping("/test-mandatory")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void testMandatoryWithoutTransaction(
//            @RequestParam Long accountId,
//            @RequestParam BigDecimal amount) {
//        accountService.debit(accountId, amount);
//    }

}
