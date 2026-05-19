package com.bank.digital_banking.service.interfaces;

import com.bank.digital_banking.dto.TransactionResponseDto;
import com.bank.digital_banking.model.Transaction;
import com.bank.digital_banking.utils.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void logTransaction(Long accountId, Double amount, TransactionType transactionType);
    Page<TransactionResponseDto> getTransactions(Long accountId, Pageable pageable);
}
