package com.bank.digital_banking.controller;

import com.bank.digital_banking.dto.AccountResponseDto;
import com.bank.digital_banking.dto.TransactionRequestDto;
import com.bank.digital_banking.dto.TransferRequestDto;
import com.bank.digital_banking.service.interfaces.AccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final AccountService accountService;

    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping("/{id}/deposit")
    public AccountResponseDto deposit(@PathVariable Long id, @Valid @RequestBody TransactionRequestDto request) {
        return accountService.deposit(id, request.getAmount());
    }
    @PostMapping("{id}/withdraw")
    public AccountResponseDto withdraw(@PathVariable Long id, @Valid @RequestBody TransactionRequestDto request) {
        return accountService.withdraw(id, request.getAmount());
    }
    @PostMapping("/transferMoney")
    public void transferMoney(@Valid @RequestBody TransferRequestDto request) {
        accountService.transferMoney(request.getFromId(), request.getToId(), request.getAmount());
    }
}
