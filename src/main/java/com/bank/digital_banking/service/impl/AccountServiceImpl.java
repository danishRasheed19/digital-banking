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
    public AccountResponseDto createAccount(AccountRequestDto account) {
        Account saved=  account_repository.save(AccountMapper.toEntity(account));
        return AccountMapper.toDto(saved);
    }
    public List<AccountResponseDto> getAllAccounts() {
        return account_repository.findAll()
                .stream()
                .map(AccountMapper :: toDto)
                .toList();
    }
    public AccountResponseDto getAccountById(Long id) {
         Account accountFounded = account_repository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
         return AccountMapper.toDto(accountFounded);
    }
    private Account getAccountEntityById(Long id){
        return account_repository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }
    public AccountResponseDto deposit(Long id, Double amount) {
        Account account = getAccountEntityById(id);
        account.setBalance(account.getBalance() + amount);
        account_repository.save(account);
        transactionService.logTransaction(id,amount,TransactionType.DEPOSIT);
        return AccountMapper.toDto(account);
    }
    public AccountResponseDto withdraw(Long id, Double amount) {
        Account account = getAccountEntityById(id);
        validateTransaction(account,amount);
        account.setBalance(account.getBalance() - amount);
        account_repository.save(account);
        transactionService.logTransaction(id,amount,TransactionType.WITHDRAW);
        return AccountMapper.toDto(account);
    }

    @Transactional
    public void transferMoney(Long fromId, Long toId, Double amount) {
        Account fromAccount  = getAccountEntityById(fromId);
        Account toAccount  = getAccountEntityById(toId);
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
