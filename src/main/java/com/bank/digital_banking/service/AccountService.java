package com.bank.digital_banking.service;

import com.bank.digital_banking.exception.*;
import com.bank.digital_banking.model.Account;
import com.bank.digital_banking.model.Transaction;
import com.bank.digital_banking.repo.AccountRepository;
import com.bank.digital_banking.repo.TransactionRepository;
import com.bank.digital_banking.utils.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class AccountService {
    private final AccountRepository account_repository;
    private final TransactionRepository transaction_repository;

    public AccountService(AccountRepository account_repository,TransactionRepository transaction_repository) {
        this.account_repository = account_repository;
        this.transaction_repository = transaction_repository;
    }
    public Account createAccount(Account account) {
        return account_repository.save(account);
    }
    public List<Account> getAllAccounts() {
        return account_repository.findAll();
    }
    public Account getAccountById(Long id) {
        return account_repository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }
    public Account deposit(Long id, Double amount) {
        Account account = getAccountById(id);
        account.setBalance(account.getBalance() + amount);
        account_repository.save(account);

        transaction_repository.save(
                Transaction.builder()
                        .accountId(id)
                        .amount(amount)
                        .type(TransactionType.DEPOSIT)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
        return account;
    }
    public Account withdraw(Long id, Double amount) {
        Account account = getAccountById(id);
        validateTransaction(account,amount);
        account.setBalance(account.getBalance() - amount);
        account_repository.save(account);
        transaction_repository.save(
                Transaction.builder()
                        .accountId(id)
                        .amount(amount)
                        .type(TransactionType.WITHDRAW)
                        .timestamp(LocalDateTime.now())
                        .build()
            );

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
        transaction_repository.save(
                Transaction.builder()
                        .accountId(fromAccount.getId())
                        .amount(amount)
                        .type(TransactionType.TRANSFER_OUT)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
        transaction_repository.save(
                Transaction.builder()
                        .accountId(toAccount.getId())
                        .amount(amount)
                        .type(TransactionType.TRANSFER_IN)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
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
