package com.bank.digital_banking.service.interfaces;

import com.bank.digital_banking.model.Transaction;
import com.bank.digital_banking.utils.enums.TransactionStatus;
import com.bank.digital_banking.utils.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {
    void logTransaction(Long accountId, Double amount, TransactionType transactionType, TransactionStatus transactionStatus);
    Page<Transaction> getTransactions(Long accountId, Pageable pageable);
    List<Transaction> getTransactions(Long accountId);
    List<Transaction> getTransactionsByAccountIdAndStatus(Long accountId,TransactionStatus status);
}
