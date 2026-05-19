package com.bank.digital_banking.service.impl;

import com.bank.digital_banking.dto.TransactionResponseDto;
import com.bank.digital_banking.model.Transaction;
import com.bank.digital_banking.repo.TransactionRepository;
import com.bank.digital_banking.service.interfaces.TransactionService;
import com.bank.digital_banking.utils.TransactionMapper;
import com.bank.digital_banking.utils.TransactionType;
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
    public void logTransaction(Long accountId, Double amount, TransactionType transactionType) {
        transactionRepository.save(
                Transaction.builder()
                        .accountId(accountId)
                        .amount(amount)
                        .type(transactionType)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public Page<TransactionResponseDto> getTransactions(Long accountId, Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findByAccountId(accountId,pageable);
       return transactions.map(transaction -> TransactionMapper.toDto(transaction));
    }
}
