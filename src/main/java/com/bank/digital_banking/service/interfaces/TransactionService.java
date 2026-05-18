package com.bank.digital_banking.service.interfaces;

import com.bank.digital_banking.dto.TransactionResponseDto;
import com.bank.digital_banking.model.Transaction;
import com.bank.digital_banking.utils.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void logTransaction(Long accountId, Double amount, TransactionType transactionType);
    List<TransactionResponseDto> getTransactions(Long accountId);
}
