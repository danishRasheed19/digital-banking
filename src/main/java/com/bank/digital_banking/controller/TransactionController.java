package com.bank.digital_banking.controller;

import com.bank.digital_banking.dto.AccountResponseDto;
import com.bank.digital_banking.dto.TransactionRequestDto;
import com.bank.digital_banking.dto.TransactionResponseDto;
import com.bank.digital_banking.dto.TransferRequestDto;
import com.bank.digital_banking.service.interfaces.AccountService;
import com.bank.digital_banking.service.interfaces.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
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
    @GetMapping("{id}/getTransactions")
    public Page<TransactionResponseDto> getTransactions(@PathVariable Long id, @PageableDefault(size = 10) Pageable pageable) {
        return transactionService.getTransactions(id,pageable);
    }
}
