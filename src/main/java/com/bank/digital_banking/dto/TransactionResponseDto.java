package com.bank.digital_banking.dto;

import com.bank.digital_banking.utils.enums.TransactionStatus;
import com.bank.digital_banking.utils.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionResponseDto {
    private Long accountId;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private Double amount;
    private LocalDateTime timestamp;
}
