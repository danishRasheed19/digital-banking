package com.bank.digital_banking.service.impl;

import com.bank.digital_banking.dto.AccountRequestDto;
import com.bank.digital_banking.dto.AccountResponseDto;
import com.bank.digital_banking.exception.*;
import com.bank.digital_banking.model.Account;
import com.bank.digital_banking.repo.AccountRepository;
import com.bank.digital_banking.service.interfaces.AccountService;
import com.bank.digital_banking.service.interfaces.TransactionService;
import com.bank.digital_banking.utils.AccountMapper;
import com.bank.digital_banking.utils.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository account_repository;
    private final TransactionService transactionService;

    public AccountServiceImpl(AccountRepository account_repository,TransactionService transactionService) {
        this.account_repository = account_repository;
        this.transactionService=transactionService;
    }
    @Override
    public Account createAccount(Account account) {
        return account_repository.save(account);
    }
    @Override
    public List<Account> getAllAccounts() {
        return account_repository.findAll();
    }
    @Override
    public Account getAccountById(Long id) {
         Account accountFound = account_repository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
         return accountFound;
    }
    @Override
    public Account deposit(Long id, Double amount) {
        Account account = getAccountById(id);
        account.setBalance(account.getBalance() + amount);
        account_repository.save(account);
        transactionService.logTransaction(id,amount,TransactionType.DEPOSIT);
        return account;
    }
    @Override
    public Account withdraw(Long id, Double amount) {
        Account account = getAccountById(id);
        validateTransaction(account,amount);
        account.setBalance(account.getBalance() - amount);
        account_repository.save(account);
        transactionService.logTransaction(id,amount,TransactionType.WITHDRAW);
        return account;
    }

    @Transactional
    public void transferMoney(Long fromId, Long toId, Double amount) {
        Account fromAccount  = getAccountById(fromId);
        Account toAccount  = getAccountById(toId);
        if (fromId.equals(toId)) {
            throw new SameAccountTransferException("Cannot transfer money to same account");
        }
        validateTransaction(fromAccount,amount);
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        account_repository.save(fromAccount);
        account_repository.save(toAccount);
        transactionService.logTransaction(fromAccount.getId(),amount,TransactionType.TRANSFER_OUT);
        transactionService.logTransaction(toAccount.getId(),amount,TransactionType.TRANSFER_IN);
    }
    private void validateTransaction(Account account, double amount) {

        if (amount <= 0) {
            throw new InvalidTransactionException("Amount must be greater than zero");
        }

        if (amount > account.getLimitPerTransaction()) {
            throw new TransactionLimitException("Transaction limit exceeded");
        }

        if (account.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds in account");
        }
    }
}
