package com.bank.digital_banking.utils;

import com.bank.digital_banking.dto.AccountResponseDto;
import com.bank.digital_banking.dto.TransactionRequestDto;
import com.bank.digital_banking.dto.TransactionResponseDto;
import com.bank.digital_banking.model.Account;
import com.bank.digital_banking.model.Transaction;

public class TransactionMapper {

    public static TransactionResponseDto toDto(Transaction transaction) {
        return TransactionResponseDto.builder()
                .accountId(transaction.getAccountId())
                .transactionType(transaction.getType())
                .amount(transaction.getAmount())
                .timestamp(transaction.getTimestamp())
                .build();
    }
}
