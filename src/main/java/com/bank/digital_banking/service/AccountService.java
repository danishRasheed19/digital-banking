package com.bank.digital_banking.service;

import com.bank.digital_banking.model.Account;
import com.bank.digital_banking.repo.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountService {
    private final AccountRepository account_repository;

    public AccountService(AccountRepository account_repository) {
        this.account_repository = account_repository;
    }
    public Account createAccount(Account account) {
        account.setBalance(0.0);
        return account_repository.save(account);
    }
    public List<Account> getAllAccounts() {
        return account_repository.findAll();
    }
    public Account getAccountById(Long id) {
        return account_repository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
