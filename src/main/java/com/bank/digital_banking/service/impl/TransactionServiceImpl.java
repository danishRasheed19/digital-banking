package com.bank.digital_banking.service.impl;

import com.bank.digital_banking.model.Transaction;
import com.bank.digital_banking.repo.TransactionRepository;
import com.bank.digital_banking.service.interfaces.TransactionService;
import com.bank.digital_banking.utils.enums.TransactionStatus;
import com.bank.digital_banking.utils.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    TransactionRepository transactionRepository;
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    @Override
    public void logTransaction(Long accountId, Double amount, TransactionType transactionType, TransactionStatus transactionStatus) {
        transactionRepository.save(
                Transaction.builder()
                        .accountId(accountId)
                        .amount(amount)
                        .type(transactionType)
                        .status(transactionStatus)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public Page<Transaction> getTransactions(Long accountId, Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findByAccountId(accountId,pageable);
       return transactions;
    }
    @Override
    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
    @Override
    public List<Transaction> getTransactionsByAccountIdAndStatus(Long accountId, TransactionStatus status) {
        return transactionRepository.findByAccountIdAndStatus(accountId,status);
    }
}
