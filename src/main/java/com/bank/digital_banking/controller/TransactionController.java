package com.bank.digital_banking.controller;

import com.bank.digital_banking.dto.AccountResponseDto;
import com.bank.digital_banking.service.interfaces.AccountService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final AccountService accountService;

    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping("/{id}/deposit")
    public AccountResponseDto deposit(@PathVariable Long id, @RequestParam Double amount) {
        return accountService.deposit(id, amount);
    }
    @PostMapping("{id}/withdraw")
    public AccountResponseDto withdraw(@PathVariable Long id, @RequestParam Double amount) {
        return accountService.withdraw(id, amount);
    }
    @PostMapping("/transferMoney")
    public void transferMoney(@RequestParam Long from, @RequestParam Long to, @RequestParam Double amount) {
        accountService.transferMoney(from,to,amount);
    }
}
