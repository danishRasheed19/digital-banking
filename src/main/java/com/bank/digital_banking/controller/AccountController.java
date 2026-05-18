package com.bank.digital_banking.controller;
import com.bank.digital_banking.dto.AccountRequestDto;
import com.bank.digital_banking.dto.AccountResponseDto;
import com.bank.digital_banking.service.interfaces.AccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping
    public AccountResponseDto insertAccount(@Valid @RequestBody AccountRequestDto account) {
        return accountService.createAccount(account);
    }

    @GetMapping
    public List<AccountResponseDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }
    @GetMapping("/{id}")
    public AccountResponseDto getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }
}
