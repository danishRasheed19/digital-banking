package com.bank.digital_banking.service.impl;


import com.bank.digital_banking.exception.*;
import com.bank.digital_banking.model.Account;
import com.bank.digital_banking.model.Transaction;
import com.bank.digital_banking.repo.AccountRepository;
import com.bank.digital_banking.service.impl.transactionRules.TransactionValidator;
import com.bank.digital_banking.service.interfaces.AccountService;
import com.bank.digital_banking.service.interfaces.TransactionService;
import com.bank.digital_banking.utils.enums.TransactionStatus;
import com.bank.digital_banking.utils.enums.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository account_repository;
    private final TransactionService transactionService;
    private final TransactionValidator transactionValidator;

    public AccountServiceImpl(AccountRepository account_repository,TransactionService transactionService, TransactionValidator transactionValidator) {
        this.account_repository = account_repository;
        this.transactionService=transactionService;
        this.transactionValidator=transactionValidator;
    }

    @Override
    @Transactional
    public Account createAccount(Account account) {
        Account saved = account_repository.save(account);
        if(saved.getBalance() != null && saved.getBalance()>0){
            transactionService.logTransaction(saved.getId(),saved.getBalance(),TransactionType.DEPOSIT,TransactionStatus.SUCCESS);
        }
        return saved;
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
    @Transactional
    public Account deposit(Long id, Double amount) {
        Account account = getAccountById(id);
        transactionValidator.validate(account,amount,TransactionType.DEPOSIT);
        try{
            Double currentBalance = Optional.ofNullable(account.getBalance()).orElse(0.0);
            account.setBalance(currentBalance+amount);
            Account saved = account_repository.save(account);
            transactionService.logTransaction(id,amount,TransactionType.DEPOSIT,TransactionStatus.SUCCESS);
            return saved;
        }catch(Exception e){
            transactionService.logTransaction(id,amount,TransactionType.DEPOSIT,TransactionStatus.FAILED);
            throw (e);
        }
    }
    @Override
    @Transactional
    public Account withdraw(Long id, Double amount) {
        Account account = getAccountById(id);
        transactionValidator.validate(account,amount,TransactionType.WITHDRAW);
        try{
            Double currentBalance =  Optional.ofNullable(account.getBalance()).orElse(0.0);
            account.setBalance(currentBalance - amount);
            Account saved = account_repository.save(account);
            transactionService.logTransaction(id,amount,TransactionType.WITHDRAW,TransactionStatus.SUCCESS);
            return saved;
        } catch(Exception e){
            transactionService.logTransaction(id,amount,TransactionType.WITHDRAW,TransactionStatus.FAILED);
            throw (e);
        }
    }

    @Transactional
    public void transferMoney(Long fromId, Long toId, Double amount) {
        if (fromId.equals(toId)) {
            throw new SameAccountTransferException("Cannot transfer money to same account");
        }
        Account fromAccount  = getAccountById(fromId);
        Account toAccount  = getAccountById(toId);
        transactionValidator.validate(fromAccount,amount,TransactionType.TRANSFER_OUT);
        try{
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
            account_repository.save(fromAccount);
            account_repository.save(toAccount);
            transactionService.logTransaction(fromAccount.getId(),amount,TransactionType.TRANSFER_OUT,TransactionStatus.SUCCESS);
            transactionService.logTransaction(toAccount.getId(),amount,TransactionType.TRANSFER_IN,TransactionStatus.SUCCESS);
        }catch(Exception e){
            transactionService.logTransaction(
                    fromId,
                    amount,
                    TransactionType.TRANSFER_OUT,
                    TransactionStatus.FAILED
            );

            transactionService.logTransaction(
                    toId,
                    amount,
                    TransactionType.TRANSFER_IN,
                    TransactionStatus.FAILED
            );

            throw e;
        }
    }
    @Override
    public Double calculateBalance(Long accountId){
        account_repository.findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
        List<Transaction> transactions= transactionService.getTransactionsByAccountIdAndStatus(accountId, TransactionStatus.SUCCESS);
        return transactions
                .stream()
                .mapToDouble(transaction ->
                        transaction.getAmount() * transaction.getType().getMultiplier())
                .sum();
    }
}
