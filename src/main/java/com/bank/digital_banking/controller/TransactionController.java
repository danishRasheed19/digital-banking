package com.bank.digital_banking.controller;

import com.bank.digital_banking.model.Account;
import com.bank.digital_banking.service.AccountService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final AccountService accountService;

    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id, @RequestParam Double amount) {
        return accountService.deposit(id, amount);
    }
    @PostMapping("{id}/withdraw")
    public Account withdraw(@PathVariable Long id, @RequestParam Double amount) {
        return accountService.withdraw(id, amount);
    }
    @PostMapping("/transferMoney")
    public void transferMoney(@RequestParam Long from, @RequestParam Long to, @RequestParam Double amount) {
        accountService.transferMoney(from,to,amount);
    }
}
