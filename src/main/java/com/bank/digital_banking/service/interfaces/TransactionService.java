package com.bank.digital_banking.service.interfaces;

import com.bank.digital_banking.model.Transaction;
import com.bank.digital_banking.utils.TransactionType;

import java.math.BigDecimal;

public interface TransactionService {
    void logTransaction(Long accountId, Double amount, TransactionType transactionType);
}
